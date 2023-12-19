package ljakovic.simplebudgeting.budgetaccount.service;

import jakarta.persistence.EntityNotFoundException;
import ljakovic.simplebudgeting.appuser.model.AppUser;
import ljakovic.simplebudgeting.appuser.repo.AppUserRepo;
import ljakovic.simplebudgeting.budgetaccount.dto.BudgetAccountDto;
import ljakovic.simplebudgeting.budgetaccount.mapper.BudgetAccountMapper;
import ljakovic.simplebudgeting.budgetaccount.model.BudgetAccount;
import ljakovic.simplebudgeting.budgetaccount.repo.BudgetAccountRepo;
import ljakovic.simplebudgeting.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BudgetAccountService {

    @Autowired
    private BudgetAccountRepo accountRepo;
    @Autowired
    private AppUserRepo appUserRepo;
    @Autowired
    private BudgetAccountMapper mapper;
    @Autowired
    private UserUtil userUtil;

    public BudgetAccount getBudgetAccountById(Integer id) {
        return accountRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Budget account not found"));
    }

    public BudgetAccountDto getById(Integer id) {
        BudgetAccount budgetAccount = accountRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Budget account not found"));

        return mapper.mapTo(budgetAccount);
    }

    public List<BudgetAccountDto> getUserAccounts() {
        final Integer loggedInUserId = userUtil.getLoggedInUserId();

        return accountRepo.findByUserId(loggedInUserId).stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    public BudgetAccountDto getAccountByIdUserId(Integer id, Integer userId) {
        return mapper.mapTo(accountRepo.findByIdUserId(id, userId));
    }

    public BudgetAccountDto createBudgetAccount() {
        final Integer loggedInUserId = userUtil.getLoggedInUserId();

        final AppUser appUser = appUserRepo.findById(loggedInUserId)
                .orElseThrow(() -> new EntityNotFoundException("App user not found"));

        final BudgetAccount account = BudgetAccount.builder()
                .totalResources(500D)
                .user(appUser)
                .dateCreated(new Date())
                .dateModified(new Date())
                .build();

        accountRepo.save(account);

        return mapper.mapTo(account);
    }
}
