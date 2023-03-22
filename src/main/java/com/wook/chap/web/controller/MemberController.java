package com.wook.chap.web.controller;

import com.wook.chap.entity.Member;
import com.wook.chap.web.form.SignUpForm;
import com.wook.chap.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/signUp")
    public String signUpPage(Model model) {
        SignUpForm signUpForm = new SignUpForm();
        model.addAttribute("signUpForm", signUpForm);
        return "/form/signUpForm";
    }

    @PostMapping("/signUp")
    public String signUp(@Valid @ModelAttribute("signUpForm") SignUpForm signUpForm,
                         BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.debug("Field 에러 발생");
            log.debug("{}",bindingResult.getAllErrors());
            log.debug("에러 갯수 : {}",bindingResult.getAllErrors().size());

            return "/form/signUpForm";
        }

        /**
         * 비밀번호 검증 로직 불통과 시 뷰로 전달
         */
        if (!signUpForm.validatePassword(bindingResult)) {
            return "/form/signUpForm";
        }

        try {
            Member member = memberService.signUp(signUpForm);
        } catch (RuntimeException ex) {
            log.debug("회원가입 예외 발생 : {}",ex.getMessage());
            bindingResult.reject("member.duplicate");
        }

        if (bindingResult.hasErrors()) {
            return "/form/signUpForm";
        }

        return "redirect:/login";


    }


}
