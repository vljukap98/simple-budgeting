package ljakovic.simplebudgeting.aggregation.endpoint;

import ljakovic.simplebudgeting.aggregation.dto.AggregationDto;
import ljakovic.simplebudgeting.aggregation.dto.AggregationResDto;
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

    @PostMapping("/money-spent/budget-account/{id}")
    public ResponseEntity<List<AggregationResDto>> aggregateMoneySpent(
            @RequestBody AggregationDto reqDto,
            @PathVariable Integer id
            ) {

        return ResponseEntity.ok(service.aggregateMoneySpent(id, reqDto)) ;
    }

    @PostMapping("/money-earned/budget-account/{id}")
    public ResponseEntity<List<AggregationResDto>> aggregateMoneyEarned(
            @RequestBody AggregationDto reqDto,
            @PathVariable Integer id
    ) {
        return ResponseEntity.ok(service.aggregateMoneyEarned(id, reqDto)) ;
    }

}
