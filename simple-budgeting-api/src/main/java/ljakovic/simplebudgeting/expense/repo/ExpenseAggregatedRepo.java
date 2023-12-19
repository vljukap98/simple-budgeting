package ljakovic.simplebudgeting.expense.repo;

import jakarta.persistence.Tuple;
import ljakovic.simplebudgeting.aggregation.dto.AggregationTypeEnum;

import java.util.List;

public interface ExpenseAggregatedRepo {

    public List<Tuple> aggregate(
            Integer accountId,
            String startDate,
            String endDate,
            AggregationTypeEnum aggregationType);
}
