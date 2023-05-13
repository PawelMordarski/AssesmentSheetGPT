package pl.coderslab.assessmentsheetgpt.sheet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.net.URI;

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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sheet with number " + request.number() + "not exist");
        }
        SheetSummary summary = sheetManager.update(request);
        return ResponseEntity.ok(summary);
    }




}
