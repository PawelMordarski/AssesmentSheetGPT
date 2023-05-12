package pl.coderslab.assessmentsheetgpt.note;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notes")
@Slf4j
@RequiredArgsConstructor
public class NoteController {

    private final NoteRepository noteRepository;

    @PostMapping
    public Note addNote(@RequestBody Note note) {
        noteRepository.save(note);
        log.debug("Added note: {}", note);
        return note;
    }

}