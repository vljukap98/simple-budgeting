package ljakovic.simplebudgeting.aggregation.endpoint;

import ljakovic.simplebudgeting.aggregation.dto.AggregationDto;
import ljakovic.simplebudgeting.aggregation.dto.AggregationResDto;
import ljakovic.simplebudgeting.aggregation.service.AggregationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/aggregation")
public class AggregationEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(AggregationEndpoint.class);

    @Autowired
    private AggregationService service;

    @PostMapping("/money-spent/budget-account/{id}")
    public ResponseEntity<List<AggregationResDto>> aggregateMoneySpent(
            @RequestBody AggregationDto reqDto,
            @PathVariable Integer id
            ) {
        LOGGER.info("Request POST body: {}", reqDto);
        return ResponseEntity.ok(service.aggregateMoneySpent(id, reqDto)) ;
    }

    @PostMapping("/money-earned/budget-account/{id}")
    public ResponseEntity<List<AggregationResDto>> aggregateMoneyEarned(
            @RequestBody AggregationDto reqDto,
            @PathVariable Integer id
    ) {
        LOGGER.info("Request POST body: {}", reqDto);
        return ResponseEntity.ok(service.aggregateMoneyEarned(id, reqDto)) ;
    }

}
