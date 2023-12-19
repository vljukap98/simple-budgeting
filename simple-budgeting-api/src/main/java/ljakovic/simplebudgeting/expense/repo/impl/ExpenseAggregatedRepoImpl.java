package ljakovic.simplebudgeting.expense.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import ljakovic.simplebudgeting.aggregation.dto.AggregationTypeEnum;
import ljakovic.simplebudgeting.expense.repo.ExpenseAggregatedRepo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ExpenseAggregatedRepoImpl implements ExpenseAggregatedRepo {

    @Autowired
    private EntityManager em;

    @Override
    public List<Tuple> aggregate(
            Integer accountId,
            String startDate,
            String endDate,
            AggregationTypeEnum aggregationType) {
        final StringBuilder query = new StringBuilder();

        query.append("SELECT ");

        if (aggregationType.equals(AggregationTypeEnum.MONTHLY)) {
            query.append("EXTRACT(MONTH FROM e.date_created) AS month, ");
        }

        query.append("EXTRACT(YEAR from e.date_created) AS year, ")
                .append("SUM(e.amount) AS total_amount ")
                .append("FROM expense e ")
                .append("WHERE e.account_id = ").append(accountId).append(" ")
                .append("AND DATE(e.date_created) BETWEEN '").append(startDate).append("' ")
                .append("AND '").append(endDate).append("' ");

        if (aggregationType.equals(AggregationTypeEnum.MONTHLY)) {
            query.append("GROUP BY month, year");
        } else if (aggregationType.equals(AggregationTypeEnum.YEARLY)) {
            query.append("GROUP BY year");
        }

        System.out.println(query);

        Query q = em.createNativeQuery(query.toString(), Tuple.class);

        return q.getResultList();
    }
}
