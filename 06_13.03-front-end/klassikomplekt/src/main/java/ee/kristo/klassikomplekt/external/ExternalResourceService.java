package ee.kristo.klassikomplekt.external;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Map;

@Service
public class ExternalResourceService {

    private final RestClient restClient;
    private final String judgesUrl;
    private final String locationsUrl;

    public ExternalResourceService(
            @Value("${mockapi.judges-url:}") String judgesUrl,
            @Value("${mockapi.locations-url:}") String locationsUrl
    ) {
        this.restClient = RestClient.create();
        this.judgesUrl = judgesUrl;
        this.locationsUrl = locationsUrl;
    }

    public List<Map<String, Object>> getJudges() {
        return fetch(judgesUrl, List.of(
                Map.of("id", "1", "name", "Katrin Kuusk", "country", "EST", "role", "peakohtunik"),
                Map.of("id", "2", "name", "Martin Tamm", "country", "EST", "role", "ajamootja")
        ));
    }

    public List<Map<String, Object>> getLocations() {
        return fetch(locationsUrl, List.of(
                Map.of("id", "1", "name", "Kadrioru staadion", "city", "Tallinn"),
                Map.of("id", "2", "name", "Tamme staadion", "city", "Tartu")
        ));
    }

    private List<Map<String, Object>> fetch(String url, List<Map<String, Object>> fallback) {
        if (!StringUtils.hasText(url)) {
            return fallback;
        }

        try {
            List<Map<String, Object>> response = restClient.get()
                    .uri(url)
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {
                    });
            return response == null ? List.of() : response;
        } catch (RestClientException ex) {
            throw new RuntimeException("MockAPI päring ebaõnnestus: " + ex.getMessage());
        }
    }
}
