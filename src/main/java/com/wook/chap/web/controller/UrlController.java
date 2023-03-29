package com.wook.chap.web.controller;

import com.wook.chap.domain.service.UrlService;
import com.wook.chap.exception.NotValidUrlException;
import com.wook.chap.web.utils.UrlInspector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.regex.Pattern;


@Controller
@Slf4j
@RequiredArgsConstructor
public class UrlController {

    private final UrlInspector urlInspector;

    private final UrlService urlService;

    private static String pattern = "^[a-z0-9A-Z]{6}$";




    @PostMapping("/uri")
    public String urlInspection(String requestURI, Principal principal, Model model, @Value("${domain-name}") String domain) {
        Long loginMemberId = Long.parseLong(principal.getName());
        log.debug("현재 변환하고 싶은 Original URI : {}",requestURI);
        log.debug("현재 로그인한 멤버 ID : {}",loginMemberId);

        if (urlInspector.urlValidator(requestURI)) {
            String shortUrl = urlService.convertUrlAndSave(requestURI, loginMemberId);
            model.addAttribute("shortUrl", domain+shortUrl);
            return "redirect:/";
        }

        model.addAttribute("invalidUrl", true);

        return "redirect:/";
    }

    @GetMapping("/{shortsUrl}")
    public RedirectView redirectUrl(@PathVariable("shortsUrl") String shortsUrl) {
        if (!Pattern.matches(pattern,shortsUrl)) {
            throw new NotValidUrlException("Shorts URL의 형식이 잘못되었습니다.");
        }

        String originalUrl = urlService.returnOriginalUrl(shortsUrl);
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl(originalUrl);
        return redirectView;
    }






}
