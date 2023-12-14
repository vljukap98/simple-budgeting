package ljakovic.simplebudgeting.budgetaccount.mapper;

import ljakovic.simplebudgeting.appuser.dto.AppUserDto;
import ljakovic.simplebudgeting.budgetaccount.dto.BudgetAccountDto;
import ljakovic.simplebudgeting.budgetaccount.model.BudgetAccount;
import org.springframework.stereotype.Component;

@Component
public class BudgetAccountMapper {
    public BudgetAccountDto mapTo(BudgetAccount budgetAccount) {
        AppUserDto appUserDto = AppUserDto.builder()
                .id(budgetAccount.getUser().getId().toString())
                .firstName(budgetAccount.getUser().getFirstName())
                .lastName(budgetAccount.getUser().getLastName())
                .build();

        return BudgetAccountDto.builder()
                .id(budgetAccount.getId().toString())
                .resources(budgetAccount.getTotalResources().toString())
                .dateCreated(budgetAccount.getDateCreated().toString())
                .dateModified(budgetAccount.getDateModified().toString())
                .user(appUserDto)
                .build();

    }
}
