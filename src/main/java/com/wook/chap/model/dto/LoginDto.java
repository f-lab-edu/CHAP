package com.wook.chap.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginDto {

    @NotBlank
    private String membername;

    @NotBlank
    private String password;
}
