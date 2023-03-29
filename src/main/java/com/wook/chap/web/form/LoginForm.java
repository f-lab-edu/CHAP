package com.wook.chap.web.form;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank
    private String membername;

    @NotBlank
    private String password;
}
