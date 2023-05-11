package pl.coderslab.assessmentsheetgpt.monitor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonitorRepository extends JpaRepository<Monitor, Long> {
    Optional<Monitor> findByName(String name);
}
