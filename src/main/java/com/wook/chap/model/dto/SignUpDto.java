package com.wook.chap.model.dto;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Slf4j
public class SignUpDto {

    @NotBlank
    @Size(min=8, max=20)
    private String membername;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String repeatPassword;

    @NotBlank
    @Size(min=5, max=10)
    private String nickname;


    public boolean validatePassword(BindingResult result) {
        regex(result);
        if (result.hasErrors()) {
            return false;
        }
        return true;
    }

    private void regex(BindingResult result) {

        Pattern pattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,16}$");//8자~16자 영문+특수문자+숫자 포함
        Matcher matcher = pattern.matcher(this.password);

        if (!matcher.find()) {
            log.debug("비밀번호는 영문+특수문자+숫자 8자로 구성되어야 합니다");
            result.rejectValue("password","password.wrongexpression");
        } else if (!this.password.equals(repeatPassword)) {
            log.debug("비밀번호가 서로 일치하지 않습니다.");
            result.reject("password.notmatch");
        }

    }

}
