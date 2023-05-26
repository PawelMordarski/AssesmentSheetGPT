package pl.coderslab.assessmentsheetgpt.sheet;

import org.hibernate.validator.constraints.Range;
import pl.coderslab.assessmentsheetgpt.note.Note;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UpdateSheetRequest (
        @NotBlank String number,
        @NotBlank String proces,
        @Range(min = 0, max = 100) Integer rate,
        @Size(max = 2000) String body,
        @AssertTrue Boolean edited,
        Note note){
}
