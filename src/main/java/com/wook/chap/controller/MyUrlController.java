package com.wook.chap.controller;

import com.wook.chap.model.dto.*;
import com.wook.chap.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class MyUrlController {

    private UrlService urlService;



    @GetMapping("/myUrl")
    public String myUrl() {

        return "myUrl";
    }

    @GetMapping
    public String getPagingByCondition(@ModelAttribute SearchConditionDto searchCondition,
                                       BindingResult bindingResult,
                                       @ModelAttribute PageRequestDto pageRequest,
                                       Principal principal,
                                       Model model) {

        if (!searchCondition.isValidTime()) {
            bindingResult.reject("invalid.time");
        }

        if (bindingResult.hasErrors()) {
            return "myUrl";
        }

        Long loginMemberId = Long.parseLong(principal.getName());
        Pageable pageable = PageRequest.of(pageRequest.getPage() - 1, pageRequest.getSize(), pageRequest.getIsAsc() == 1
                ? Sort.by("createdTime").ascending() : Sort.by("createdTime").descending());

        SearchConditionInfo searchConditionInfo =
                new SearchConditionInfo(searchCondition.getSearchWord(), searchCondition.getStartDate(), searchCondition.getEndDate());

        PageResultDto<UrlInfo> urlInfoPage = urlService.pagingMyUrl(loginMemberId, searchConditionInfo, pageable);
        model.addAttribute("urlInfoPage", urlInfoPage);

        return "myUrl";

    }


}