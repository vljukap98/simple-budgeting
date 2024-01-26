package ljakovic.simplebudgeting.income.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import ljakovic.simplebudgeting.aggregation.dto.AggregationTypeEnum;
import ljakovic.simplebudgeting.income.repo.IncomeAggregatedRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IncomeAggregatedRepoImpl implements IncomeAggregatedRepo {

    private static final Logger LOGGER = LoggerFactory.getLogger(IncomeAggregatedRepoImpl.class);

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
            query.append("EXTRACT(MONTH FROM i.date_created) AS month, ");
        }

            query.append("EXTRACT(YEAR from i.date_created) AS year, ")
                .append("SUM(i.amount) AS total_amount ")
                .append("FROM income i ")
                .append("WHERE i.account_id = ").append(accountId).append(" ")
                .append("AND DATE(i.date_created) BETWEEN '").append(startDate).append("' ")
                .append("AND '").append(endDate).append("' ");

        if (aggregationType.equals(AggregationTypeEnum.MONTHLY)) {
            query.append("GROUP BY month, year");
        } else if (aggregationType.equals(AggregationTypeEnum.YEARLY)) {
            query.append("GROUP BY year");
        }

        LOGGER.info("{}", query);

        Query q = em.createNativeQuery(query.toString(), Tuple.class);

        return q.getResultList();
    }
}
