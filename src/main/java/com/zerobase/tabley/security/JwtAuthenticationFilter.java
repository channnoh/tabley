package com.zerobase.tabley.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    public static final String TOKEN_HEADER = "Authorization";
    public static final String TOKEN_PREFIX = "Bearer ";  // for 인증 타입 확인(JWT) 한 칸 공백 // value

    private final TokenProvider tokenProvider;

    /**
     * 요청이 들어올 때마다 controller로 넘어가기 전에 filter 처리 해줌
     * OncePerRequestFilter 상속받기 떄문에 동일한 요청에 대해 여러번 필터링 되는 것 방지 -> 한 번만
     */


    /**
     * Spring Security 컨텍스트에서 인증 토큰을 처리하기 위해 사용됨
     * 클라이언트가 보낸 HTTP 요청의 헤더에서 인증 토큰을 추출하고, 이 토큰이 유효한지 검사한 후,
     * 유효하다면 해당 사용자를 인증하여 Spring Security 컨텍스트에 사용자 인증 정보를 설정
     * 이 후 요청 처리 과정에서 현재 유저가 인증된 것으로 간주
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = resolveTokenFromRequest(request);

        // 토큰 유효성
        if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
            Authentication auth = tokenProvider.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(auth);

            log.info(String.format("[%s] -> %s", tokenProvider.getUserEmail(token)), request.getRequestURI());
        }

        filterChain.doFilter(request, response);
    }

    /**
     * request 헤더에서 인증 토크 가져와서 유효한 토큰이 포함되어 있는지 확인 후 반환
     * @param request
     * @return
     */

    private String resolveTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);

        if (!ObjectUtils.isEmpty(token) && token.startsWith(TOKEN_PREFIX)) {
            return token.substring(TOKEN_PREFIX.length());
        }
        return null;
    }
}
