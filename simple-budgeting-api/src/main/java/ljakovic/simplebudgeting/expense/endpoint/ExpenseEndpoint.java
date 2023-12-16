package ljakovic.simplebudgeting.expense.endpoint;

import jakarta.websocket.server.PathParam;
import ljakovic.simplebudgeting.expense.dto.ExpenseDto;
import ljakovic.simplebudgeting.expense.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/expense")
public class ExpenseEndpoint {

    @Autowired
    private ExpenseService service;

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(UUID.fromString(id)));
    }

    @GetMapping("/budget-account/{id}")
    public ResponseEntity<List<ExpenseDto>> getExpensesForBudgetAccount(@PathVariable String id) {
        return ResponseEntity.ok(service.getByBudgetAccountId(UUID.fromString(id)));
    }

    @PostMapping("/add")
    public ResponseEntity<ExpenseDto> addExpense(@RequestBody ExpenseDto dto) {
        return ResponseEntity.ok(service.createExpense(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<ExpenseDto> updateExpense(@RequestBody ExpenseDto dto) {
        return ResponseEntity.ok(service.update(dto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteExpense(@PathVariable String id) {
        service.delete(UUID.fromString(id));
    }
}
