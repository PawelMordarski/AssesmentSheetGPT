package pl.coderslab.assessmentsheetgpt.note;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.assessmentsheetgpt.sheet.Sheet;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {
    Optional<Note> findById(Integer Id);

    Note findByComment(String comment);

}
