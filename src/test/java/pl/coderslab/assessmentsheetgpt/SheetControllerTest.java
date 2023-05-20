package pl.coderslab.assessmentsheetgpt;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.coderslab.assessmentsheetgpt.sheet.CreateSheetRequest;
import pl.coderslab.assessmentsheetgpt.sheet.SheetController;
import pl.coderslab.assessmentsheetgpt.sheet.SheetManager;
import pl.coderslab.assessmentsheetgpt.sheet.SheetSummary;

import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SheetController.class)
class SheetControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    SheetManager sheetManager;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void whenCreateSheet_success() throws Exception{
        CreateSheetRequest request =
                new CreateSheetRequest("1/1/23", 50, "ZODK", "Paweł", "Średnio", "POŻYCZKA");
        SheetSummary summary =
                new SheetSummary("2/1/23", "Rafał", "BANKOWOŚĆ", "tak se", 30, LocalDateTime.now(), 2);
        when(sheetManager.create(request)).thenReturn(summary);

        mockMvc
                .perform(
                        post("/api/sheets")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(header().string(LOCATION, "/api/sheets"));
    }

    @Test
    public void whenCreateSheet_withMissingNumber_fail() throws Exception {
        CreateSheetRequest request =
                new CreateSheetRequest(null, 50, "ZODK", "Paweł", "Średnio", "Pożyczka");

        mockMvc
                .perform(
                        post("/api/sheets")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest());

    }
}
