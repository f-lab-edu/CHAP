package com.wook.chap.config;

import com.wook.chap.jwt.*;
import com.wook.chap.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {


    private final CustomUserDetailsService service;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, TokenProvider tokenProvider,
                                           JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                                           JwtAccessDeniedHandler jwtAccessDeniedHandler) throws Exception {

        http
                .csrf().disable()

                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .antMatchers("/","/login", "/signUp").permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider))

                .and()
                .logout()
                .deleteCookies(JwtFilter.AUTHORIZATION_HEADER);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {


        // Random한 Salt 값을 받아서 같이 hashing을 이용하는 BCryptPasswordEncoder 사용
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return (web) -> {
            web.ignoring().mvcMatchers(
                    "/h2-console/**",
                    "/favicon.ico",
                    "/css/**",
                    "/js/**",
                    "/images/**"
            );
        };
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        daoAuthenticationProvider.setUserDetailsService(service);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
}
