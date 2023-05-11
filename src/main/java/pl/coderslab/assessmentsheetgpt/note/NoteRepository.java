package pl.coderslab.assessmentsheetgpt.note;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.assessmentsheetgpt.team.Team;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {
    List<Note> findAllByTeamAndSheetIsNotNull(Team team);
}
