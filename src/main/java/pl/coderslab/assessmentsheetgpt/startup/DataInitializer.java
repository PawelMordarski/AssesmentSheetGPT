package pl.coderslab.assessmentsheetgpt.startup;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import pl.coderslab.assessmentsheetgpt.monitor.Monitor;
import pl.coderslab.assessmentsheetgpt.monitor.MonitorRepository;
import pl.coderslab.assessmentsheetgpt.note.Note;
import pl.coderslab.assessmentsheetgpt.note.NoteRepository;
import pl.coderslab.assessmentsheetgpt.sheet.Sheet;
import pl.coderslab.assessmentsheetgpt.sheet.SheetRepository;
import pl.coderslab.assessmentsheetgpt.team.Team;
import pl.coderslab.assessmentsheetgpt.team.TeamRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class DataInitializer {

    private final MonitorRepository monitorRepository;
    private final NoteRepository noteRepository;
    private final SheetRepository sheetRepository;
    private final TeamRepository teamRepository;

    @EventListener
    @Transactional
    public void loadInitialData(ContextRefreshedEvent contextRefreshedEvent) {
        Monitor monitor1 =
                Monitor.builder()
                        .name("Paweł")
                        .personalNumber("pi11808")
                        .build();
        monitorRepository.save(monitor1);

        Monitor monitor2 =
                Monitor.builder()
                        .name("Rafał")
                        .personalNumber("pi11818")
                        .build();
        monitorRepository.save(monitor2);

        Team team1 =
                Team.builder()
                        .name("ZODK")
                        .department("PO")
                        .build();
        teamRepository.save(team1);

        Team team2 =
                Team.builder()
                        .name("ZOP")
                        .department("PO")
                        .build();
        teamRepository.save(team2);

        Team team3 =
                Team.builder()
                        .name("CC")
                        .department("CRM")
                        .build();
        teamRepository.save(team3);

        Team team4 =
                Team.builder()
                        .name("ZOR")
                        .department("CRM")
                        .build();
        teamRepository.save(team4);


        Sheet sheet1 =
                Sheet.builder()
                        .number("1")
                        .proces("POŻYCZKA")
                        .rate(90)
                        .body("Pozytywny z uwagami")
                        .monitor(monitor1)
                        .team(team1)
                        .build();

        sheetRepository.save(sheet1);

        Sheet sheet2 =
                Sheet.builder()
                        .number("2")
                        .proces("DEPO")
                        .rate(50)
                        .body("Pozytywny z uwagami")
                        .monitor(monitor1)
                        .team(team1)
                        .build();

        sheetRepository.save(sheet2);


        Sheet sheet3 =
                Sheet.builder()
                        .number("3")
                        .proces("BANKOWOŚĆ")
                        .rate(0)
                        .body("Negatywny")
                        .monitor(monitor1)
                        .team(team1)
                        .build();

        sheetRepository.save(sheet3);


        Sheet sheet4 =
                Sheet.builder()
                        .number("4")
                        .proces("POŻYCZKA")
                        .rate(90)
                        .body("Pozytywny z uwagami")
                        .monitor(monitor2)
                        .team(team2)
                        .build();

        sheetRepository.save(sheet4);


        Note note1=
                Note.builder()
                        .comment("Brak akceptacji")
                        .team(team1)
                        .build();
        noteRepository.save(note1);

        Note note2=
                Note.builder()
                        .comment("Częściowa akceptacja")
                        .team(team1)
                        .build();
        noteRepository.save(note2);



    }

}
