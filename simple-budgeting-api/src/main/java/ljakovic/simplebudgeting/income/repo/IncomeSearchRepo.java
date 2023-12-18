package ljakovic.simplebudgeting.income.repo;

import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface IncomeSearchRepo {

    List<String> searchIncomeByAccount(
            String budgedAccountId,
            Double amountMin,
            Double amountMax,
            Date startDate,
            Date endDate,
            Pageable pageable);
}
