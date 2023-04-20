package com.wook.chap.model.dto;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.util.StringUtils.*;

@Data
public class SearchConditionInfo {

    private String searchWord;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    public SearchConditionInfo(String searchWord, String startDate, String endDate) {
        this.searchWord = searchWord;
        this.startDate = formDateToLocalDateTime(startDate);
        this.endDate = formDateToLocalDateTime(endDate);
    }

    public LocalDateTime formDateToLocalDateTime(String date) {

        if (!hasText(date)) {
            return null;
        }

        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate localDate = LocalDate.parse(date, format);

        return LocalDateTime.of(localDate, LocalTime.MIN);
    }
}
