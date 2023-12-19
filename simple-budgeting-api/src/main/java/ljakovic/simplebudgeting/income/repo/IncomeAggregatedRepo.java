package ljakovic.simplebudgeting.income.repo;

import jakarta.persistence.Tuple;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface IncomeAggregatedRepo {

    public List<Tuple> aggregateMonthly(Integer accountId, String startDate, String endDate);
}
