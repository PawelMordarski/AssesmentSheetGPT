package pl.coderslab.assessmentsheetgpt.sheet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sheets")
@Slf4j
@RequiredArgsConstructor
public class SheetController {

    private final SheetManager sheetManager;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateSheetRequest request) {
        SheetSummary summary = sheetManager.create(request);
        return ResponseEntity.created(URI.create("/api/sheets/" + summary.number())).build();
    }

    @PutMapping("/{number}")
    public ResponseEntity<SheetSummary> update(@PathVariable String number, @RequestBody @Valid UpdateSheetRequest request) {
        if (!number.equals(request.number())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sheet with number " + request.number() + " not exist");
        }
        SheetSummary summary = sheetManager.update(request);
        return ResponseEntity.ok(summary);
    }

    @GetMapping("/{number}")
    public ResponseEntity<SheetSummary> getByNumber(@PathVariable String number) {
        Optional<SheetSummary> sheetSummary = sheetManager.getByNumber(number);
        return sheetSummary.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @GetMapping
    public List<SheetSummary> getAllSheets() {
        List<SheetSummary> sheets = sheetManager.getAll();
        log.debug("Collected {} sheets", sheets.size());
        return sheets;
    }


    @GetMapping("/lowest")
    public List<Sheet> getSheetWithLowestRate() {
        return sheetManager.getSheetWithLowestRate(1);
    }

    @GetMapping("/highest")
    public List<Sheet> getSheetWithHighestRate() {
        return sheetManager.getSheetWithHighestRate(1);
    }

    @GetMapping("/by_team/{teamId}")
    public List<Sheet> getSheetsByTeam(@PathVariable Integer teamId) {
        return sheetManager.getSheetsByTeam(teamId);
    }

    @GetMapping("/by_monitor/{monitorId}")
    public List<Sheet> getSheetsByMonitor(@PathVariable Integer monitorId) {
        return sheetManager.getSheetsByMonitor(monitorId);
    }

    @DeleteMapping("/{number}")
    public void delete(@PathVariable String number) {
        try {
            sheetManager.delete(number);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sheet with number " + number + " not exist");
        }

    }


}