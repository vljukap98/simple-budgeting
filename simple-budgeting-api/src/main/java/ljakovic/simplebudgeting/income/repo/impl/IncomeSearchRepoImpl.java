package ljakovic.simplebudgeting.income.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import ljakovic.simplebudgeting.income.repo.IncomeSearchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class IncomeSearchRepoImpl implements IncomeSearchRepo {

    @Autowired
    private EntityManager em;

    @Override
    public List<String> searchIncomeByAccount(
            String budgedAccountId,
            Double amountMin,
            Double amountMax,
            Date startDate,
            Date endDate,
            Pageable pageable) {
        final StringBuilder query = new StringBuilder();

        query.append("SELECT e.id AS id FROM expense e")
                .append(" INNER JOIN budget_account ba on ba.id = e.account_id");

        final StringBuilder where = new StringBuilder();
        where(where, budgedAccountId, amountMin, amountMax, startDate, endDate);
        query.append(where);

        Query q = em.createNativeQuery(query.toString(), String.class);
        params(q, amountMin, amountMax, startDate, endDate);
        q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());

        return q.getResultList();
    }

    private void where(
            StringBuilder where,
            String budgedAccountId,
            Double amountMin,
            Double amountMax,
            Date startDate,
            Date endDate) {
        where.append(" WHERE")
                .append(" ba.id = '").append(budgedAccountId).append("'");

        if (amountMin != null) {
            where.append(" AND e.amount >= :amountMin");
        }
        if (amountMax != null) {
            where.append(" AND e.amount <= :amountMax");
        }
        if (startDate != null) {
            where.append(" AND e.dateCreated >= startDate");
        }
        if (endDate != null) {
            where.append(" AND e.dateCreated <= endDate");
        }
    }

    private void params(
            Query q,
            Double amountMin,
            Double amountMax,
            Date startDate,
            Date endDate) {

        if (amountMin != null) {
            q.setParameter("amountMin", amountMin);
        }
        if (amountMax != null) {
            q.setParameter("amountMax", amountMax);
        }
        if (startDate != null) {
            q.setParameter("startDate", startDate);
        }
        if (endDate != null) {
            q.setParameter("endDate", endDate);
        }
    }
}
