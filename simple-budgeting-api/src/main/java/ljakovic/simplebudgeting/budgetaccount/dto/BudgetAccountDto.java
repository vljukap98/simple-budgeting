package ljakovic.simplebudgeting.budgetaccount.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ljakovic.simplebudgeting.appuser.dto.AppUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BudgetAccountDto {

    private String id;
    private String resources;
    private String dateCreated;
    private String dateModified;
    private AppUserDto user;

    //TODO: add incomes and expenses
}
