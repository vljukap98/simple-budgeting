package ljakovic.simplebudgeting.expense.repo;

import ljakovic.simplebudgeting.expense.model.Expense;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface ExpenseFilterRepository {

    List<String> searchExpensesByAccount(
            String budgedAccountId,
            Double amountMin,
            Double amountMax,
            Date startDate,
            Date endDate,
            List<String> categoryNames,
            List<String> categoryTypes,
            Pageable pageable);
}
