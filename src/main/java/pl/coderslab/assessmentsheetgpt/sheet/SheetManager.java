package pl.coderslab.assessmentsheetgpt.sheet;

import java.util.List;
import java.util.Optional;

public interface SheetManager {

    SheetSummary create(CreateSheetRequest request);

    SheetSummary update(UpdateSheetRequest request);

    List<SheetSummary> getAll();

    Optional<SheetSummary> getByNumber();

}
