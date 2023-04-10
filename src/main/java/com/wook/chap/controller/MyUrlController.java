package com.wook.chap.controller;

import com.wook.chap.model.dto.*;
import com.wook.chap.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("myUrl")
@RequiredArgsConstructor
public class MyUrlController {


    private final UrlService urlService;



    @GetMapping
    public String getPagingByCondition(@ModelAttribute("searchCondition") SearchConditionDto searchCondition,
                                       @ModelAttribute("pageRequest") PageRequestDto pageRequest,
                                       Principal principal,
                                       Model model) {

        if (!searchCondition.isValidTime()) {
            model.addAttribute("isInvalidTime",true);
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

    @PostMapping("/delete/{urlId}")
    @ResponseBody
    public ResponseEntity<?> deleteUrl(@PathVariable("urlId") Long urlId,
                                       Principal principal) {

        Long loginMemberId = Long.parseLong(principal.getName());

        if (!urlService.isMyUrl(loginMemberId, urlId)) {
            return ResponseEntity.badRequest().body("해당 URL가 존재하지 않습니다.");
        }

        return ResponseEntity.status(302).build();


    }

}
