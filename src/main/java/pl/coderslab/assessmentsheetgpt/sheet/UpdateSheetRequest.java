package pl.coderslab.assessmentsheetgpt.sheet;

import org.hibernate.validator.constraints.Range;
import pl.coderslab.assessmentsheetgpt.monitor.Monitor;
import pl.coderslab.assessmentsheetgpt.note.Note;
import pl.coderslab.assessmentsheetgpt.team.Team;


import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record UpdateSheetRequest (
        @NotBlank String number,
        @NotBlank String proces,
        @Range(min = 0, max = 100) Integer rate,
        @Size(max = 2000) String body,
        @AssertTrue Boolean edited,
        Note note,
        Team team,
        Monitor monitor){
}
