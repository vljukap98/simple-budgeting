package ljakovic.simplebudgeting.expense.mapper;

import ljakovic.simplebudgeting.budgetaccount.dto.BudgetAccountDto;
import ljakovic.simplebudgeting.category.dto.CategoryDto;
import ljakovic.simplebudgeting.expense.dto.ExpenseDto;
import ljakovic.simplebudgeting.expense.model.Expense;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {
    public ExpenseDto mapTo(Expense expense) {
        final CategoryDto categoryDto = CategoryDto.builder()
                .id(expense.getCategory().getId().toString())
                .name(expense.getCategory().getName())
                .description(expense.getCategory().getDescription())
                .build();

        final BudgetAccountDto accountDto = BudgetAccountDto.builder()
                .id(expense.getAccount().getId().toString())
                .build();

        return ExpenseDto.builder()
                .id(expense.getId().toString())
                .amount(expense.getAmount().toString())
                .dateCreated(expense.getDateCreated().toString())
                .account(accountDto)
                .category(categoryDto)
                .build();
    }
}
