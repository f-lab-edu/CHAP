package com.wook.chap.security.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private TokenProvider tokenProvider;

    public JwtSecurityConfig(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    /**
     * UsernamePasswordAuthenticationFilter(로그인 로직) 전에 Jwt 토큰을 관리(TokenProvider에 위임)하는 필터 주입
     * 토큰을 실제로 유효성 검사하고 Authentication 객체를 생성하는 클래스는 TokenProvider임
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
    }
}
