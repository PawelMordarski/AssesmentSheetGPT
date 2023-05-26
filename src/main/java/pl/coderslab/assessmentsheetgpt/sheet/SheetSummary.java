package pl.coderslab.assessmentsheetgpt.sheet;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

public record SheetSummary(
        String number,
        String monitor,
        String proces,
        String body,
        Integer rate,
        @JsonFormat(pattern = "eee MMM dd hh:mm:ss yyyy") LocalDateTime addedOn,
        @Nullable String comment) {
}
