package job_agent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import job_agent.service.TinyFishService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class AgentController {

    @Autowired
    private TinyFishService tinyFishService;

    // Home Page
    @GetMapping("/")
    public String home() {
        return "index";
    }

    // Handle form submission
    @PostMapping("/run-agent")
    public String runAgent(@RequestParam("role") String role, Model model) {

        String response = tinyFishService.runAgent(role);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            JsonNode jobs = root.path("result").path("job_listings");

            model.addAttribute("jobs", jobs);
            model.addAttribute("note", root.path("result").path("note").asText());

        } catch (Exception e) {
            model.addAttribute("error", "Parsing error");
        }

        model.addAttribute("role", role) ;
        return "index";
    }
}