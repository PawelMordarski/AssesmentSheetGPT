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
import java.util.Optional;
import java.util.stream.Collectors;

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
                        .findByName(request.teamName())
                        .orElseThrow(
                                () -> new IllegalArgumentException("No team called " + request.teamName()));

        List<Note> noteList = noteRepository.findAllByTeam(team);

        Sheet sheet =
                Sheet.builder()
                        .number(request.number())
                        .monitor(monitor)
                        .team(team)
                        .rate(request.rate())
                        .proces(request.proces())
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

    @Override
    @Transactional
    public SheetSummary update(UpdateSheetRequest request) {
        return sheetRepository
                .findByNumber(request.number())
                .map(
                        sheet -> {
                            sheet.setProces(request.proces());
                            if (request.body() != null) {
                                sheet.setBody(request.body());
                            }
                            if (request.noted() != null) {
                                if (sheet.isNoted()) {
                                    throw new IllegalStateException("Sheet was noted");
                                }
                                sheet.setNoted(true);
                            }

                            sheet.setBody(request.body());
                            sheet.setRate(request.rate());
                            return sheet;
                        })
                .map(sheetRepository::save)
                .map(this::toSummary)
                .orElseThrow(() -> new IllegalArgumentException("No sheets with number: " + request.number()));
    }

    @Override
    public List<SheetSummary> getAll() {
        List<Sheet> sheets = sheetRepository.findAll();
        return sheets
                .stream()
                .map(this::toSummary)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Optional<SheetSummary> getByNumber(String number) {
        Optional<Sheet> sheet = sheetRepository.findByNumber(number);
        return sheet
                .map(this::toSummary);
    }

    private SheetSummary toSummary(Sheet sheet){
        return new SheetSummary(
                sheet.getNumber(),
                sheet.getMonitor().getName(),
                sheet.getProces(),
                sheet.getTeam().getName(),
                sheet.getRate(),
                sheet.getAddedOn(),
                sheet.getNoteList().size());


    }


}
