package ljakovic.simplebudgeting.aggregation.endpoint;

import ljakovic.simplebudgeting.aggregation.dto.AggregationDto;
import ljakovic.simplebudgeting.aggregation.service.AggregationService;
import ljakovic.simplebudgeting.expense.dto.ExpenseDto;
import ljakovic.simplebudgeting.income.dto.IncomeDto;
import ljakovic.simplebudgeting.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/aggregation")
public class AggregationEndpoint {

    @Autowired
    private AggregationService service;

    @PostMapping("/money-earned/budget-account/{id}")
    public ResponseEntity<List<ExpenseDto>> aggregateMoneyEarned(
            @RequestBody AggregationDto reqDto,
            @RequestParam(value = "size", defaultValue = "10", required = false) int size,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @PathVariable String id
            ) {
        final Pageable pageable = PageRequest.of(page > 0 ? page - 1 : page, size);

        return ResponseEntity.ok(service.aggregateMoneySpent(pageable, UUID.fromString(id), reqDto)) ;
    }

}
