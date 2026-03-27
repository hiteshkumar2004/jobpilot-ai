package job_agent.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TinyFishService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${tinyfish.api.key}")
    private String API_KEY;

    @Value("${tinyfish.api.url}")
    private String API_URL;

    public String runAgent(String role) {

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-API-Key", API_KEY);
            headers.setContentType(MediaType.APPLICATION_JSON);

            String goal = "Navigate to naukri.com, search for " + role + " jobs, " +
                    "filter by 0-1 years experience, open top 3 job listings, " +
                    "extract company name, salary, skills required, and apply if possible";

            String body = "{ \"url\": \"https://www.naukri.com/\", \"goal\": \"" + goal + "\" }";

            HttpEntity<String> request = new HttpEntity<>(body, headers);

            ResponseEntity<String> response =
                    restTemplate.exchange(API_URL, HttpMethod.POST, request, String.class);

            return response.getBody();

        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR: " + e.getMessage();
        }
    }
}