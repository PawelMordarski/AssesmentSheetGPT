package pl.coderslab.assessmentsheetgpt.sheet;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SheetRepository extends JpaRepository<Sheet, Long> {
        Optional<Sheet> findByNumber(String number);
}
