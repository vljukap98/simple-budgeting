package ljakovic.simplebudgeting.expense.endpoint;

import ljakovic.simplebudgeting.expense.dto.ExpenseDto;
import ljakovic.simplebudgeting.expense.dto.ExpenseSearchDto;
import ljakovic.simplebudgeting.expense.service.ExpenseService;
import ljakovic.simplebudgeting.search.SearchDto;
import ljakovic.simplebudgeting.search.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    @Deprecated
    public ResponseEntity<List<ExpenseDto>> getExpensesForBudgetAccount(@PathVariable String id) {
        return ResponseEntity.ok(service.getByBudgetAccountId(UUID.fromString(id)));
    }

    @PostMapping("/budget-account/{id}")
    public ResponseEntity<List<ExpenseDto>> searchExpensesForBudgetAccountPageable(
        @RequestParam(value = "size", defaultValue = "10", required = false) int size,
        @RequestParam(value = "page", defaultValue = "0", required = false) int page,
        @RequestBody SearchDto searchDto,
        @PathVariable String id
    ) {
        Sort sort = null;
        final Pageable pageable;
        if (searchDto != null && searchDto.getSortDirection() != null
                && searchDto.getSortProperty() != null) {
            if (searchDto.getSortDirection().equals(SortDirection.ASC)) {
                sort = Sort.by(searchDto.getSortProperty()).ascending();
            } else if (searchDto.getSortDirection().equals(SortDirection.DESC)) {
                sort = Sort.by(searchDto.getSortProperty()).descending();
            }
            pageable = PageRequest.of(page > 0 ? page - 1 : page, size, sort);
        } else {
            pageable = PageRequest.of(page > 0 ? page - 1 : page, size);
        }

        return ResponseEntity.ok(service.getByBudgetAccountIdPageable(pageable, UUID.fromString(id)));
    }

    @PostMapping("/search/budget-account/{id}")
    public ResponseEntity<List<ExpenseDto>> searchFilteredExpensesForBudgetAccountPageable(
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestBody ExpenseSearchDto searchDto,
            @PathVariable String id
    ) {
        final Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, size);

        return ResponseEntity.ok(service.searchByBudgetAccountIdPageable(pageable, UUID.fromString(id), searchDto));
    }

    @PostMapping("/add")
    public ResponseEntity<ExpenseDto> addExpense(@RequestBody ExpenseDto dto) {
        return ResponseEntity.ok(service.createExpense(dto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteExpense(@PathVariable String id) {
        service.delete(UUID.fromString(id));
    }
}
