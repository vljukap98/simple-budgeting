package ljakovic.simplebudgeting.budgetaccount.endpoint;

import ljakovic.simplebudgeting.budgetaccount.dto.BudgetAccountDto;
import ljakovic.simplebudgeting.budgetaccount.service.BudgetAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/budget-account")
public class BudgetAccountEndpoint {

    @Autowired
    private BudgetAccountService service;

    @GetMapping("/{id}")
    public ResponseEntity<BudgetAccountDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(UUID.fromString(id)));
    }

    @GetMapping("/user-accounts")
    public ResponseEntity<List<BudgetAccountDto>> getUserAccounts() {
        return ResponseEntity.ok(service.getUserAccounts());
    }

    @GetMapping("/user/{userId}/{accountId}")
    public ResponseEntity<BudgetAccountDto> getAccountByIdUserId(@PathVariable String userId, @PathVariable String accountId) {
        return ResponseEntity.ok(service.getAccountByIdUserId(UUID.fromString(accountId), UUID.fromString(userId)));
    }
}
