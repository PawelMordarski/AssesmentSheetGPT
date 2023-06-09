package pl.coderslab.assessmentsheetgpt;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.coderslab.assessmentsheetgpt.monitor.Monitor;
import pl.coderslab.assessmentsheetgpt.monitor.MonitorRepository;
import pl.coderslab.assessmentsheetgpt.note.NoteRepository;
import pl.coderslab.assessmentsheetgpt.sheet.CreateSheetRequest;
import pl.coderslab.assessmentsheetgpt.sheet.SheetManagerImplementation;
import pl.coderslab.assessmentsheetgpt.sheet.SheetRepository;
import pl.coderslab.assessmentsheetgpt.sheet.SheetSummary;
import pl.coderslab.assessmentsheetgpt.team.Team;
import pl.coderslab.assessmentsheetgpt.team.TeamRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SheetManagerImplementationTest {

    @Mock
    NoteRepository noteRepository;
    @Mock
    SheetRepository sheetRepository;
    @Mock
    MonitorRepository monitorRepository;
    @Mock
    TeamRepository teamRepository;

    @InjectMocks
    SheetManagerImplementation sheetManagerImplementation;

    @Test
    public void whenCreateSheet_withNonExistingMonitor_fail() {
        when(monitorRepository.findByName("Non_exist")).thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () ->
                                sheetManagerImplementation.create(
                                        new CreateSheetRequest("1/1/23", 50, "ZODK", "Non_exist", "Średnio", "POŻYCZKA")));
        assertThat(exception).hasMessageContaining("No monitor called Non_exist");
    }

    @Test
    public void whenCreateSheet_withNonExistingTeam_fail() {
        Monitor monitor = Monitor.builder().name("Paweł").build();
        when(monitorRepository.findByName("Paweł")).thenReturn(Optional.of(monitor));
        when(teamRepository.findByName("Non_exists"))
                .thenReturn(Optional.empty());

        RuntimeException exception =
                assertThrows(
                        RuntimeException.class,
                        () ->
                                sheetManagerImplementation.create(
                                        new CreateSheetRequest(
                                                "1/1/23", 50, "Non_exists", "Paweł", "Średnio", "POŻYCZKA")));
        assertThat(exception)
                .hasMessageContaining("No team called Non_exists");
    }

    @Test
    public void whenCreateSheet_success() {
        Monitor monitor = Monitor.builder().name("Paweł").build();
        when(monitorRepository.findByName("Paweł"))
                .thenReturn(Optional.of(monitor));

        Team team = Team.builder().name("ZODK").build();
        when(teamRepository.findByName("ZODK"))
                .thenReturn(Optional.of(team));

        SheetSummary sheetSummary =
                sheetManagerImplementation.create(
                        new CreateSheetRequest("1234", 50, "ZODK", "Paweł", "Średnio", "POŻYCZKA"));

        assertThat(sheetSummary)
                .hasFieldOrPropertyWithValue("number", "1234")
                .hasFieldOrPropertyWithValue("monitor", "Paweł")
                .hasFieldOrPropertyWithValue("proces", "POŻYCZKA")
                .hasFieldOrPropertyWithValue("body", "ZODK")
                .hasFieldOrPropertyWithValue("rate", 50);
    }


}
