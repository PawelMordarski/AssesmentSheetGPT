package pl.coderslab.assessmentsheetgpt.sheet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.assessmentsheetgpt.monitor.Monitor;
import pl.coderslab.assessmentsheetgpt.monitor.MonitorRepository;
import pl.coderslab.assessmentsheetgpt.note.Note;
import pl.coderslab.assessmentsheetgpt.note.NoteRepository;
import pl.coderslab.assessmentsheetgpt.team.Team;
import pl.coderslab.assessmentsheetgpt.team.TeamRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SheetManagerImplementation implements SheetManager {

    private final SheetRepository sheetRepository;
    private final NoteRepository noteRepository;
    private final TeamRepository teamRepository;
    private final MonitorRepository monitorRepository;

    @Transactional
    public SheetSummary create(CreateSheetRequest request) {
        Monitor monitor =
                monitorRepository
                        .findByName(request.monitorName())
                        .orElseThrow(
                                () -> new IllegalArgumentException("No monitor called " + request.monitorName()));
        Team team =
                teamRepository
                        .findByNameAndMonitor(request.teamName(), monitor)
                        .orElseThrow(
                                () -> new IllegalArgumentException("No team called " + request.teamName()));

        List<Note> noteList = noteRepository.findAllByTeamAndSheetIsNotNull(team);

        if (noteList.isEmpty()) {
            throw new IllegalStateException("No notes");
        }

        Sheet sheet =
                Sheet.builder()
                        .number(request.number())
                        .monitor(monitor)
                        .team(team)
                        .rate(request.rate())
                        .procesType(request.procesType())
                        .body(request.body())
                        .noted(false)
                        .noteList(noteList)
                        .build();
        sheetRepository.save(sheet);

        for (Note note : noteList){
            note.setSheet(sheet);
        }

        noteRepository.saveAll(noteList);
                return toSummary(sheet);

    }

    private SheetSummary toSummary(Sheet sheet){
        return new SheetSummary(
                sheet.getNumber(),
                sheet.getMonitor().getName(),
                sheet.getProcesType(),
                sheet.getTeam().getName(),
                sheet.getRate(),
                sheet.getAddedOn(),
                sheet.getNoteList().size());


    }


}
