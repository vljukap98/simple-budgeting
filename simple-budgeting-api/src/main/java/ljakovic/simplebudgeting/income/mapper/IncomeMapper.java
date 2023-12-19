package ljakovic.simplebudgeting.income.mapper;

import ljakovic.simplebudgeting.budgetaccount.dto.BudgetAccountDto;
import ljakovic.simplebudgeting.income.dto.IncomeDto;
import ljakovic.simplebudgeting.income.model.Income;
import org.springframework.stereotype.Component;

@Component
public class IncomeMapper {
    public IncomeDto mapTo(Income income) {
        final BudgetAccountDto accountDto = BudgetAccountDto.builder()
                .id(income.getAccount().getId())
                .build();

        return IncomeDto.builder()
                .id(income.getId())
                .amount(income.getAmount())
                .dateCreated(income.getDateCreated())
                .account(accountDto)
                .build();
    }
}
