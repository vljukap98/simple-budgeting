package ljakovic.simplebudgeting.expense.repo;

import jakarta.persistence.Tuple;
import ljakovic.simplebudgeting.budgetaccount.model.BudgetAccount;
import ljakovic.simplebudgeting.expense.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Integer>, ExpenseSearchRepo, ExpenseAggregatedRepo {

    @Query("SELECT e FROM Expense e WHERE e.account.id = ?1")
    List<Expense> findByBudgetAccountId(Integer id);

    Page<Expense> findByAccount(BudgetAccount account, Pageable pageable);

    @Query("SELECT " +
            "MONTH(e.dateCreated) AS month, " +
            "YEAR(e.dateCreated) AS year, " +
            "SUM(e.amount) AS total_amount " +
            "FROM Expense e " +
            "WHERE e.dateCreated >= :startDate " +
            "AND e.dateCreated <= :endDate " +
            "AND e.account.id = :accountId " +
            "GROUP BY month, year")
    List<Tuple> aggregateMonthly(@Param("accountId") Integer accountId,
                                 @Param("startDate") Date startDate,
                                 @Param("endDate") Date endDate);

    @Query("SELECT " +
            "YEAR(e.dateCreated) AS year, " +
            "SUM(e.amount) AS total_amount " +
            "FROM Expense e " +
            "WHERE e.dateCreated >= :startDate " +
            "AND e.dateCreated <= :endDate " +
            "AND e.account.id = :accountId " +
            "GROUP BY year")
    List<Tuple> aggregateYearly(@Param("accountId") Integer accountId,
                                @Param("startDate") Date startDate,
                                @Param("endDate") Date endDate);

}
