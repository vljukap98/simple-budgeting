package ljakovic.simplebudgeting.income.endpoint;

import ljakovic.simplebudgeting.income.dto.IncomeDto;
import ljakovic.simplebudgeting.income.dto.IncomeSearchDto;
import ljakovic.simplebudgeting.income.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/income")
public class IncomeEndpoint {

    @Autowired
    private IncomeService service;

    @PostMapping("/search/budget-account/{id}")
    public ResponseEntity<List<IncomeDto>> searchFilteredExpensesForBudgetAccountPageable(
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestBody IncomeSearchDto searchDto,
            @PathVariable Integer id
    ) {
        final Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, size);

        return ResponseEntity.ok(service.searchByBudgetAccountIdPageable(pageable, id, searchDto));
    }

    @PostMapping("/create")
    public ResponseEntity<IncomeDto> createIncome(@RequestBody IncomeDto dto) {
        return ResponseEntity.ok(service.createIncome(dto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteIncome(@PathVariable Integer id) {
        service.delete(id);
    }
}
