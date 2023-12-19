package ljakovic.simplebudgeting.income.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import ljakovic.simplebudgeting.income.repo.IncomeAggregatedRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class IncomeAggregatedRepoImpl implements IncomeAggregatedRepo {

    @Autowired
    private EntityManager em;

    @Override
    public List<Tuple> aggregateMonthly(Integer accountId, String startDate, String endDate) {
        final StringBuilder query = new StringBuilder();

        query.append("SELECT ")
                .append("EXTRACT(MONTH FROM i.date_created) AS month, ")
                .append("EXTRACT(YEAR from i.date_created) AS year, ")
                .append("SUM(i.amount) AS total_amount ")
                .append("FROM income i ")
                .append("WHERE i.account_id = ").append(accountId).append(" ")
                .append("AND DATE(i.date_created) BETWEEN '").append(startDate).append("' ")
                .append("AND '").append(endDate).append("' ")
                .append("GROUP BY month, year");

        System.out.println(query);

        Query q = em.createNativeQuery(query.toString(), Tuple.class);
        //q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        //q.setMaxResults(pageable.getPageSize());

        return q.getResultList();
    }
}
