package ljakovic.simplebudgeting.income.endpoint;

import ljakovic.simplebudgeting.income.dto.IncomeDto;
import ljakovic.simplebudgeting.income.dto.IncomeSearchDto;
import ljakovic.simplebudgeting.income.service.IncomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/income")
public class IncomeEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomeEndpoint.class);

    @Autowired
    private IncomeService service;

    @PostMapping("/search/budget-account/{id}")
    public ResponseEntity<List<IncomeDto>> searchFilteredExpensesForBudgetAccountPageable(
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestBody IncomeSearchDto searchDto,
            @PathVariable Integer id
    ) {
        LOGGER.info("POST request /v1/income/search/budget-account/{}", id);
        LOGGER.info("POST request body: {}", searchDto);

        final Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, size);

        return ResponseEntity.ok(service.searchByBudgetAccountIdPageable(pageable, id, searchDto));
    }

    @PostMapping("/create")
    public ResponseEntity<IncomeDto> createIncome(@RequestBody IncomeDto dto) {
        LOGGER.info("POST request /v1/income/create");
        LOGGER.info("POST request body: {}", dto);
        return ResponseEntity.ok(service.createIncome(dto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteIncome(@PathVariable Integer id) {
        LOGGER.info("POST request /v1/income/delete/{}", id);
        service.delete(id);
    }
}
