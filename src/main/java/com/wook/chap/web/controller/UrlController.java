package com.wook.chap.web.controller;

import com.wook.chap.domain.service.UrlService;
import com.wook.chap.web.utils.UrlInspector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;


@Controller
@Slf4j
@RequiredArgsConstructor
public class UrlController {

    private final UrlInspector urlInspector;

    private final UrlService urlService;


    @PostMapping("/uri")
    public String urlInspection(String requestURI, Principal principal, Model model) {
        Long loginMemberId = Long.parseLong(principal.getName());
        log.debug("현재 변환하고 싶은 Original URI : {}",requestURI);
        log.debug("현재 로그인한 멤버 ID : {}",loginMemberId);

        if (urlInspector.urlValidator(requestURI)) {
            String shortUrl = urlService.convertUrlAndSave(requestURI, loginMemberId);
            model.addAttribute("shortUrl", shortUrl);
            return "redirect:/";
        }

        model.addAttribute("invalidUrl", true);

        return "redirect:/";
    }




}
