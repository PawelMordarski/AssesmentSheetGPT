package pl.coderslab.assessmentsheetgpt.sheet;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UpdateSheetRequest (
    @NotBlank String number,
    @NotBlank String proces,
    @Size(max = 2000) String body,
    @AssertTrue Boolean noted ){
}
