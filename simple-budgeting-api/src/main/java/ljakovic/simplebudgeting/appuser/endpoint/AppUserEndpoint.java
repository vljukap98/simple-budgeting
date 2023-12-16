package ljakovic.simplebudgeting.appuser.endpoint;

import ljakovic.simplebudgeting.appuser.dto.AppUserDto;
import ljakovic.simplebudgeting.appuser.service.AppUserService;
import ljakovic.simplebudgeting.budgetaccount.dto.BudgetAccountDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/app-user")
public class AppUserEndpoint {

    @Autowired
    private AppUserService service;

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDto> getAppUserById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(UUID.fromString(id)));
    }
}
