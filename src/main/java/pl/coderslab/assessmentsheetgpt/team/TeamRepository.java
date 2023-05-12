package pl.coderslab.assessmentsheetgpt.team;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.assessmentsheetgpt.monitor.Monitor;

import java.util.Optional;

public interface TeamRepository extends JpaRepository<Team, Long> {
    Optional<Team> findByName(String name);
}
