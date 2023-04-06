package com.wook.chap.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class UrlInfo {

    private Long urlId;

    private LocalDate createdDate;

    private String originalUrl;

    private String shortsUrl;

    private Integer clickCount;

    public UrlInfo(Long urlId, LocalDateTime createdDate, String originalUrl, String shortsUrl, Integer clickCount) {
        this.urlId = urlId;
        this.createdDate = createdDate.toLocalDate();
        this.originalUrl = originalUrl;
        this.shortsUrl = shortsUrl;
        this.clickCount = clickCount;
    }
}
