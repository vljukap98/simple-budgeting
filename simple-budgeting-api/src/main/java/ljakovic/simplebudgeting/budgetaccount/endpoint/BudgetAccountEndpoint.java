package ljakovic.simplebudgeting.budgetaccount.endpoint;

import ljakovic.simplebudgeting.budgetaccount.dto.BudgetAccountDto;
import ljakovic.simplebudgeting.budgetaccount.service.BudgetAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/budget-account")
public class BudgetAccountEndpoint {

    @Autowired
    private BudgetAccountService service;

    @GetMapping("/{id}")
    public ResponseEntity<BudgetAccountDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @GetMapping("/user-accounts")
    public ResponseEntity<List<BudgetAccountDto>> getUserAccounts() {
        return ResponseEntity.ok(service.getUserAccounts());
    }

    @GetMapping("/user/{userId}/{accountId}")
    public ResponseEntity<BudgetAccountDto> getAccountByIdUserId(@PathVariable Integer userId, @PathVariable Integer accountId) {
        return ResponseEntity.ok(service.getAccountByIdUserId(accountId, userId));
    }

    @PostMapping("/create")
    public ResponseEntity<BudgetAccountDto> createBudgetAccount() {
        return ResponseEntity.ok(service.createBudgetAccount());
    }
}
