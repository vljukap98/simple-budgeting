package ljakovic.simplebudgeting.expense.repo;

import ljakovic.simplebudgeting.expense.repo.impl.dto.ExpenseQueryRepoDto;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ExpenseSearchRepo {

    List<Integer> searchExpensesByAccount(
            ExpenseQueryRepoDto dto,
            Pageable pageable);
}
