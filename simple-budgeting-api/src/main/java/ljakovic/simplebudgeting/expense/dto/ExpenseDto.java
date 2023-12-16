package ljakovic.simplebudgeting.expense.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ljakovic.simplebudgeting.budgetaccount.dto.BudgetAccountDto;
import ljakovic.simplebudgeting.category.dto.CategoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseDto {

    private String id;
    private String amount;
    private String dateCreated;
    private CategoryDto category;
    private BudgetAccountDto account;
}
