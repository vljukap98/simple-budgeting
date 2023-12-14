package ljakovic.simplebudgeting.monitor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/monitor")
public class HealthMonitor {

    @GetMapping("/health")
    public ResponseEntity<String> checkHealthStatus() {
        return ResponseEntity.ok("Health check ok");
    }
}
