package com.wook.chap.web.controller;

import com.wook.chap.web.utils.URLInspector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
@Slf4j
@RequiredArgsConstructor
public class URLInspectionController {

    private final URLInspector urlInspector;


    @PostMapping("/uri")
    public String urlInspection(String requestURI) {
        log.debug(requestURI);

        if (urlInspector.urlValidator(requestURI)) {
            return "redirect:/";
        }

        return "redirect:/";
    }


}
