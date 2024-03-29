package com.wook.chap.model.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UrlDto {

    @NotBlank
    private String requestURI;
}
