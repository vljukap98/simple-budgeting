package ljakovic.simplebudgeting.budgetaccount.mapper;

import ljakovic.simplebudgeting.appuser.dto.AppUserDto;
import ljakovic.simplebudgeting.budgetaccount.dto.BudgetAccountDto;
import ljakovic.simplebudgeting.budgetaccount.model.BudgetAccount;
import org.springframework.stereotype.Component;

@Component
public class BudgetAccountMapper {
    public BudgetAccountDto mapTo(BudgetAccount budgetAccount) {
        AppUserDto appUserDto = AppUserDto.builder()
                .id(budgetAccount.getUser().getId())
                .firstName(budgetAccount.getUser().getFirstName())
                .lastName(budgetAccount.getUser().getLastName())
                .build();

        return BudgetAccountDto.builder()
                .id(budgetAccount.getId())
                .resources(budgetAccount.getTotalResources())
                .dateCreated(budgetAccount.getDateCreated())
                .dateModified(budgetAccount.getDateModified())
                .user(appUserDto)
                .build();

    }
}
