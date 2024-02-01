package ljakovic.simplebudgeting.appuser.endpoint;

import ljakovic.simplebudgeting.appuser.dto.AppUserDto;
import ljakovic.simplebudgeting.appuser.service.AppUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/app-user")
public class AppUserEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppUserEndpoint.class);

    @Autowired
    private AppUserService service;

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDto> getAppUserById(@PathVariable Integer id) {
        LOGGER.info("GET request /v1/app-user/{}", id);
        return ResponseEntity.ok(service.getById(id));
    }
}
