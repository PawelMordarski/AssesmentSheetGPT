package pl.coderslab.assessmentsheetgpt.sheet;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record SheetSummary(
        String number,
        String monitor,
        ProcesType procesType,
        String body,
        Integer rate,
        @JsonFormat(pattern = "eee MMM dd hh:mm:ss yyyy") LocalDateTime addedOn, int size) {
}