package com.wook.chap.controller;

import com.wook.chap.model.dto.LoginDto;
import com.wook.chap.security.jwt.JwtFilter;
import com.wook.chap.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final TokenProvider tokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @GetMapping("/login/authenticate")
    public String loginPage(Model model) {
        LoginDto loginDto = new LoginDto();
        model.addAttribute("loginDto", loginDto);
        return "form/loginForm";
    }

    @PostMapping("/login/authenticate")
    public String authorize(@Valid @ModelAttribute("loginDto") LoginDto loginDto, BindingResult bindingResult,
                            HttpServletResponse response) throws UnsupportedEncodingException {

        if (bindingResult.hasErrors()) {
            log.debug("BLANK 에러 발생");
            log.debug("{}",bindingResult.getAllErrors());
            log.debug("에러 갯수 : {}",bindingResult.getAllErrors().size());

            return "form/loginForm";
        }

        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginDto.getMembername(), loginDto.getPassword());

        Authentication authentication=null;
        try {
            // Username이 존재하지 않는 에러와 password가 틀리는 에러를 구분해주기 위해 DaoAuthenticationProvider의
            // HideUserNotFoundException=false로 설정함
            log.debug("Provider : {}",authenticationManagerBuilder.getObject());

            authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        } catch (UsernameNotFoundException ex) {
            log.debug("UsernameNotFoundException 발생");
            bindingResult.reject("invalid.user");
        } catch (BadCredentialsException ex) {
            log.debug("BadCredentialsException 발생");
            bindingResult.reject("invalid.password");
        }

        if (bindingResult.hasErrors()) {
            return "form/loginForm";
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.createToken(authentication);

        Cookie cookie = new Cookie(JwtFilter.AUTHORIZATION_HEADER, URLEncoder.encode("Bearer "+jwt,"utf-8"));
        cookie.setPath("/");

        response.addCookie(cookie);

        log.debug("쿠키 생성");
        log.debug("쿠키 이름 : {}", cookie.getName());
        log.debug("쿠키 내용 : {}", cookie.getValue());

        return "redirect:/";
    }


}
