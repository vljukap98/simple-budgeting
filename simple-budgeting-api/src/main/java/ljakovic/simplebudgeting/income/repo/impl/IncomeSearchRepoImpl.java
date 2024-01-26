package ljakovic.simplebudgeting.income.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import ljakovic.simplebudgeting.income.repo.IncomeSearchRepo;
import ljakovic.simplebudgeting.income.repo.impl.dto.IncomeQueryRepoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class IncomeSearchRepoImpl implements IncomeSearchRepo {

    @Autowired
    private EntityManager em;

    @Override
    public List<Integer> searchIncomeByAccount(
            IncomeQueryRepoDto dto,
            Pageable pageable) {
        final StringBuilder query = new StringBuilder();

        query.append("SELECT e.id AS id FROM expense e")
                .append(" INNER JOIN budget_account ba on ba.id = e.account_id");

        final StringBuilder where = new StringBuilder();
        where(where, dto);
        query.append(where);

        Query q = em.createNativeQuery(query.toString(), Integer.class);
        params(q, dto);
        q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());

        return q.getResultList();
    }

    private void where(StringBuilder where, IncomeQueryRepoDto dto) {
        where.append(" WHERE")
                .append(" ba.id = '").append(dto.getBudgedAccountId()).append("'");

        if (dto.getAmountMin() != null) {
            where.append(" AND e.amount >= :amountMin");
        }
        if (dto.getAmountMax() != null) {
            where.append(" AND e.amount <= :amountMax");
        }
        if (dto.getStartDate() != null) {
            where.append(" AND e.dateCreated >= startDate");
        }
        if (dto.getEndDate() != null) {
            where.append(" AND e.dateCreated <= endDate");
        }
    }

    private void params(
            Query q,
            IncomeQueryRepoDto dto) {

        if (dto.getAmountMin() != null) {
            q.setParameter("amountMin", dto.getAmountMin());
        }
        if (dto.getAmountMax() != null) {
            q.setParameter("amountMax", dto.getAmountMax());
        }
        if (dto.getStartDate() != null) {
            q.setParameter("startDate", dto.getStartDate());
        }
        if (dto.getEndDate() != null) {
            q.setParameter("endDate", dto.getEndDate());
        }
    }
}
