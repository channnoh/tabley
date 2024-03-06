package com.zerobase.tabley.service;

import com.zerobase.tabley.domain.Member;
import com.zerobase.tabley.dto.SignInDto;
import com.zerobase.tabley.dto.SignUpDto;
import com.zerobase.tabley.exception.CustomException;
import com.zerobase.tabley.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.zerobase.tabley.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    /**
     *
     * @param email
     * @return 이메일을 식별자로 사용하여 사용자 인증을 위한 사용자 정보
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Couldn't find user"));
    }

    /**
     * 해당 email이 이미 존재하면 예외 발생, 없으면 db에 저장
     */

    public Member signUp(SignUpDto member) {
        boolean exits = this.memberRepository.existsByEmail(member.getEmail());
        if (exits) {
            throw new CustomException(ALREADY_REGISTER_USER);
        }

        member.setPassword(this.passwordEncoder.encode(member.getPassword()));
        return this.memberRepository.save(member.toEntity());
    }

    /**
     * 로그인을 위한 user의 이메일, 비밀번호 검증
     */

    public Member authenticate(SignInDto member) {

        Member user = this.memberRepository.findByEmail(member.getEmail())
                .orElseThrow(() -> new CustomException(EMAIL_NOT_FOUND));
        if (!this.passwordEncoder.matches(member.getPassword(), user.getPassword())) {
            throw new CustomException(WRONG_PASSWORD);
        }

        return user;
    }

}
