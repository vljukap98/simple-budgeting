package ljakovic.simplebudgeting.expense.repo.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import ljakovic.simplebudgeting.expense.repo.ExpenseSearchRepo;
import ljakovic.simplebudgeting.expense.repo.impl.dto.ExpenseQueryRepoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Repository
public class ExpenseSearchRepoImpl implements ExpenseSearchRepo {

    @Autowired
    private EntityManager em;

    @Override
    public List<Integer> searchExpensesByAccount(
            ExpenseQueryRepoDto dto,
            Pageable pageable) {
        final StringBuilder query = new StringBuilder();

        query.append("SELECT e.id AS id FROM expense e")
                .append(" INNER JOIN budget_account ba on ba.id = e.account_id");

        final StringBuilder join = new StringBuilder();
        join(join, dto);
        query.append(join);

        final StringBuilder where = new StringBuilder();
        where(where, dto);
        query.append(where);

        Query q = em.createNativeQuery(query.toString(), Integer.class);
        params(q, dto);
        q.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        q.setMaxResults(pageable.getPageSize());

        return q.getResultList();
    }

    private void join(StringBuilder join, ExpenseQueryRepoDto dto) {
        if (!CollectionUtils.isEmpty(dto.getCategoryTypes())) {
            join.append(" LEFT JOIN category c on c.id = e.category_id")
                    .append(" LEFT JOIN category_type ct on ct.id = c.category_type_id");
        }
        if (!CollectionUtils.isEmpty(dto.getCategoryNames())
                && join.isEmpty()) {
            join.append(" LEFT JOIN category c on c.id = e.category_id");
        }
    }

    private void where(StringBuilder where,
                       ExpenseQueryRepoDto dto) {
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
        if (!CollectionUtils.isEmpty(dto.getCategoryNames())) {
            where.append(" AND c.name IN :categoryName");
        }
        if (!CollectionUtils.isEmpty(dto.getCategoryTypes())) {
            where.append(" AND ct.name IN :categoryType");
        }
    }

    private void params(Query q,
                        ExpenseQueryRepoDto dto) {

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
        if (!CollectionUtils.isEmpty(dto.getCategoryNames())) {
            q.setParameter("categoryName", dto.getCategoryNames());
        }
        if (!CollectionUtils.isEmpty(dto.getCategoryTypes())) {
            q.setParameter("categoryType", dto.getCategoryTypes());
        }
    }
}
