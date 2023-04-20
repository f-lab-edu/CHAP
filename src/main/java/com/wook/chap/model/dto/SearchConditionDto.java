package com.wook.chap.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class SearchConditionDto {

    private String searchWord;

    private String startDate;

    private String endDate;

    public boolean isValidTime() {
        boolean isStartDateEmpty = startDate == null || startDate == "";
        boolean isEndDateEmpty = endDate == null || endDate == "";

        if (!isStartDateEmpty && !isEndDateEmpty) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            LocalDate start = LocalDate.parse(startDate, format);
            LocalDate end = LocalDate.parse(endDate, format);
            return start.isBefore(end);
        }

        return true;

    }

}
