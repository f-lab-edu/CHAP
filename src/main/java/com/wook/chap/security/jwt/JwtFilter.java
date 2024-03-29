package com.wook.chap.security.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    private TokenProvider tokenProvider;

    public JwtFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("Filter 처리 --------------------------------------------");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
//        Arrays.stream(httpServletRequest.getCookies()).forEach(cookie -> {log.debug("쿠키 종류 : {}",cookie.getValue());});
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            Authentication authentication = tokenProvider.getAuthentication(jwt);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.debug("SecurityContext에 '{}' 인증 정보를 저장했습니다, URI: {}", authentication.getName(),requestURI);
        } else {
            log.debug("유효한 JWT 토큰이 없습니다, URI: {}",requestURI);
        }

        chain.doFilter(request,response);

    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = null;
        if (request.getCookies() != null)
            bearerToken = Arrays.stream(request.getCookies()).filter((cookie -> cookie.getName().equals(AUTHORIZATION_HEADER)))
                    .findFirst().map(cookie -> cookie.getValue()).orElse(null);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer+")) {
            return bearerToken.substring(7);
        }

        return null;
    }
}
