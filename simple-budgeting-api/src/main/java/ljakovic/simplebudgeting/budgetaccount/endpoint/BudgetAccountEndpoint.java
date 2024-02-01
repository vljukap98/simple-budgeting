package ljakovic.simplebudgeting.budgetaccount.endpoint;

import ljakovic.simplebudgeting.budgetaccount.dto.BudgetAccountDto;
import ljakovic.simplebudgeting.budgetaccount.service.BudgetAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/budget-account")
public class BudgetAccountEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(BudgetAccountEndpoint.class);

    @Autowired
    private BudgetAccountService service;

    @GetMapping("/{id}")
    public ResponseEntity<BudgetAccountDto> getById(@PathVariable Integer id) {
        LOGGER.info("GET request /v1/budget-account/{}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/user-accounts")
    public ResponseEntity<List<BudgetAccountDto>> getUserAccounts() {
        LOGGER.info("GET request /v1/budget-account/user-accounts");
        return ResponseEntity.ok(service.getUserAccounts());
    }

    @GetMapping("/user/{userId}/{accountId}")
    public ResponseEntity<BudgetAccountDto> getAccountByIdUserId(@PathVariable Integer userId, @PathVariable Integer accountId) {
        LOGGER.info("GET request /v1/budget-account/user/{}/{}", userId, accountId);
        return ResponseEntity.ok(service.getAccountByIdUserId(accountId, userId));
    }

    @PostMapping("/create")
    public ResponseEntity<BudgetAccountDto> createBudgetAccount() {
        LOGGER.info("GET request /v1/budget-account/create");
        return ResponseEntity.ok(service.createBudgetAccount());
    }
}
