package com.zerobase.tabley.security;

import com.zerobase.tabley.security.exceptionhandler.MyAccessDeniedHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity // Spring Security 활성화
@EnableGlobalMethodSecurity(prePostEnabled = true) // 어노테이션을 붙여서 권한 제한 가능
@RequiredArgsConstructor
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final MyAccessDeniedHandler myAccessDeniedHandler;

    /**
     * REST API를 사용
     * JWT를 사용하기 때문에 세션 상태 정보 저장x => STATELESS
     */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/**/signup", "/**/signin").permitAll()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(myAccessDeniedHandler)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(final WebSecurity webSecurity){
        webSecurity.ignoring()
                .antMatchers();
    }

    /**
     * 사용자 자격증명 검증, 검증된 사용자 권한 부여
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
