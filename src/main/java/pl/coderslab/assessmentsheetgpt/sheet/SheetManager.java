package pl.coderslab.assessmentsheetgpt.sheet;

import java.util.List;
import java.util.Optional;

public interface SheetManager {

    SheetSummary create(CreateSheetRequest request);

    SheetSummary update(UpdateSheetRequest request);

    List<SheetSummary> getAll();

    Optional<SheetSummary> getByNumber(String number);



    void delete(String number);

    List<Sheet> getSheetWithLowestRate(int i);

    List<Sheet> getSheetWithHighestRate(int i);

    List<Sheet> getSheetsByTeam(Integer teamId);
    List<Sheet> getSheetsByMonitor(Integer MonitorId);

}
