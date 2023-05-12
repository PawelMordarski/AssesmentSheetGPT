package pl.coderslab.assessmentsheetgpt.sheet;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;

public record CreateSheetRequest(
        @NotBlank String number,
        @NotBlank String proces,
        @Range(min = 0, max = 100)Integer rate,
        @NotBlank String teamName,
        @NotBlank String monitorName,
        @NotBlank String body,
        @NotBlank ProcesType procesType) {
}
