package ljakovic.simplebudgeting.expense.repo;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ExpenseSearchRepo {

    List<Integer> searchExpensesByAccount(
            String budgedAccountId,
            Double amountMin,
            Double amountMax,
            Date startDate,
            Date endDate,
            List<String> categoryNames,
            List<String> categoryTypes,
            Pageable pageable);
}
