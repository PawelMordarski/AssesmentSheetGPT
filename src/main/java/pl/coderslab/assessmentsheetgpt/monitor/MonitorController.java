package pl.coderslab.assessmentsheetgpt.monitor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/monitors")
@Slf4j
@RequiredArgsConstructor
public class MonitorController {

    private final MonitorRepository monitorRepository;

    @PostMapping
    public Monitor addMonitor(@RequestBody Monitor monitor) {
        monitorRepository.save(monitor);
        log.debug("Added monitor: {}", monitor);
        return monitor;
    }

}
