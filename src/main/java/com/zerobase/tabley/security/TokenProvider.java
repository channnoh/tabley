package com.zerobase.tabley.security;

import com.zerobase.tabley.service.MemberService;
import com.zerobase.tabley.type.MemberType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokenProvider {

    private static final long TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // token 만료 시간(1시간)
    private static final String KEY_ROLES = "roles";
    private final MemberService memberService;

    @Value("${jwt.secret}")
    private String secretKey; // 암호화를 위한 키


    /**
     * token 생성
     * userId를 기반으로 JWT 생성, token 유효시간 설정, HS512 알고리즘 사용하여 서명
     * Claims: JWT의 payload
     * 클라이언트는 이후의 요청에서 이 토큰을 사용하여 인증가능
     * @return JWT 문자열
     */
    public String generateToken(String userId, MemberType memberType) {

        Claims claims = Jwts.claims().setSubject(userId);
        claims.put(KEY_ROLES, memberType);

        var now = new Date();
        var expiredDate = new Date(now.getTime() + TOKEN_EXPIRE_TIME);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    /**
     * JWT token으로 부터 인증 정보를 가져옴
     * userId을 JWT subject로 설정하였으므로 token을 JWT token에서 userId로 user 식별하여 UserDetail 추출
     * UsernamePasswordAuthenticationToken(UserDetail 객체, user password(JWT를 통해 이미 인증완료->빈문자열), 권한 collection)
     * @return 사용자의 정보와 사용자의 인증정보를 포함한 Authentication 객체
     */
    public Authentication getAuthentication(String jwt) {
        UserDetails userDetails = memberService.loadUserByUsername(this.getUserId(jwt));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    /**
     * token을 받아서 subject(userId) 반환
     */
    public String getUserId(String token) {
        return this.parseClaims(token).getSubject();
    }


    /**
     * JWT token 유효성 검증
     * token의 만료시간 현재랑 비교하여 검증
     * @return token 유효한지 boolean
     */
    public boolean validateToken(String token) {
        if (!StringUtils.hasText(token)) return false;

        Claims claims = this.parseClaims(token);
        return !claims.getExpiration().before(new Date());
    }


    /**
     * JWT token parsing 해서 Claims(payload) 객체 반환
     */
    private Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}


