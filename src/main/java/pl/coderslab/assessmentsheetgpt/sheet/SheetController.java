package pl.coderslab.assessmentsheetgpt.sheet;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/app/sheets")
@Slf4j
@RequiredArgsConstructor
public class SheetController {

    private final SheetManager sheetManager;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody @Valid CreateSheetRequest request) {
        SheetSummary summary = sheetManager.create(request);
        return ResponseEntity.created(URI.create("/api/sheets/" + summary.number())).build();
    }


}
