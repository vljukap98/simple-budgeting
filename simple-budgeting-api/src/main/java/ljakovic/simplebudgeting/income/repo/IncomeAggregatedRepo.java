package ljakovic.simplebudgeting.income.repo;

import jakarta.persistence.Tuple;
import ljakovic.simplebudgeting.aggregation.dto.AggregationTypeEnum;

import java.util.List;

public interface IncomeAggregatedRepo {

    public List<Tuple> aggregate(
            Integer accountId,
            String startDate,
            String endDate,
            AggregationTypeEnum aggregationType);

}
