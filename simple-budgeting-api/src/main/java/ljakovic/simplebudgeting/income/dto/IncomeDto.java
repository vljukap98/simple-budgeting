package ljakovic.simplebudgeting.income.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ljakovic.simplebudgeting.budgetaccount.dto.BudgetAccountDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class IncomeDto {

    private Integer id;
    private Double amount;
    private Date dateCreated;
    private BudgetAccountDto account;
}
