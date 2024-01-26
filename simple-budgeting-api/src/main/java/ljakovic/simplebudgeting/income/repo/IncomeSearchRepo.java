package ljakovic.simplebudgeting.income.repo;

import ljakovic.simplebudgeting.income.repo.impl.dto.IncomeQueryRepoDto;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface IncomeSearchRepo {

    List<Integer> searchIncomeByAccount(
            IncomeQueryRepoDto dto,
            Pageable pageable);
}
