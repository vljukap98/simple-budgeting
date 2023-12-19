package ljakovic.simplebudgeting.expense.repo;

import ljakovic.simplebudgeting.budgetaccount.model.BudgetAccount;
import ljakovic.simplebudgeting.expense.model.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepo extends JpaRepository<Expense, Integer>, ExpenseSearchRepo, ExpenseAggregatedRepo {

    @Query("SELECT e FROM Expense e WHERE e.account.id = ?1")
    List<Expense> findByBudgetAccountId(Integer id);

    Page<Expense> findByAccount(BudgetAccount account, Pageable pageable);
}
