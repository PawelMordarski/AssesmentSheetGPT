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
import java.util.Comparator;
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


        Sheet sheet =
                Sheet.builder()
                        .number(request.number())
                        .monitor(monitor)
                        .team(team)
                        .rate(request.rate())
                        .proces(request.proces())
                        .body(request.body())
                        .edited(false)
                        .build();
        sheetRepository.save(sheet);


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
                            if (request.edited() != null) {
                                if (sheet.isEdited()) {
                                    throw new IllegalStateException("Sheet was already edited");
                                }
                                sheet.setEdited(true);
                            }

                            sheet.setBody(request.body());
                            sheet.setRate(request.rate());
                            sheet.setNote(request.note());
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

    public List<Sheet> getSheetWithLowestRate(int limit) {
        List<Sheet> allSheets = sheetRepository.findAll();

        List<Sheet> sheets = allSheets.stream()
                .sorted(Comparator.comparing(Sheet::getRate))
                .limit(limit)
                .collect(Collectors.toList());

        return sheets;
    }



    @Transactional
    public void delete(String number) {
        sheetRepository.deleteByNumber(number);
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
                sheet.getNote().getComment());


    }


}
