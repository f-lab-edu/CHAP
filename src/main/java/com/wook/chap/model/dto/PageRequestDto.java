package com.wook.chap.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.wook.chap.model.constant.PageConstant.DEFAULT_PAGE;
import static com.wook.chap.model.constant.PageConstant.DEFAULT_SIZE;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class PageRequestDto {

    private int page = DEFAULT_PAGE;
    private int size = DEFAULT_SIZE;
    private int isAsc = 1;
}
