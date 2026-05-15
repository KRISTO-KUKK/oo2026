package ee.kristo.klassikomplekt.external;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/external")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ExternalResourceController {

    private final ExternalResourceService externalResourceService;

    @GetMapping("/judges")
    public List<Map<String, Object>> getJudges() {
        return externalResourceService.getJudges();
    }

    @GetMapping("/locations")
    public List<Map<String, Object>> getLocations() {
        return externalResourceService.getLocations();
    }
}
