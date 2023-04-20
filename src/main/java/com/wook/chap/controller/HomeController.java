package com.wook.chap.controller;

import com.wook.chap.model.dto.UrlDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        UrlDto urlDto = new UrlDto();
        model.addAttribute("urlDto", urlDto);
        return "registerUrl";
    }


}
