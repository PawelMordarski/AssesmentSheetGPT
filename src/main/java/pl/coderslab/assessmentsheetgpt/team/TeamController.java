package pl.coderslab.assessmentsheetgpt.team;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/teams")
@Slf4j
@RequiredArgsConstructor
public class TeamController {

    private final TeamRepository teamRepository;

    @PostMapping
    public Team addTeam(@RequestBody Team team) {
        teamRepository.save(team);
        log.debug("Added team: {}", team);
        return team;
    }
}
