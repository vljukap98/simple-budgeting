package ljakovic.simplebudgeting.aggregation.endpoint;

import ljakovic.simplebudgeting.aggregation.dto.AggregationDto;
import ljakovic.simplebudgeting.aggregation.dto.AggregationResDto;
import ljakovic.simplebudgeting.aggregation.service.AggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
