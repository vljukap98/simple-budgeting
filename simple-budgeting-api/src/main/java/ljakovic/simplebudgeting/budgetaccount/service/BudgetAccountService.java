package ljakovic.simplebudgeting.budgetaccount.service;

import jakarta.persistence.EntityNotFoundException;
import ljakovic.simplebudgeting.budgetaccount.dto.BudgetAccountDto;
import ljakovic.simplebudgeting.budgetaccount.mapper.BudgetAccountMapper;
import ljakovic.simplebudgeting.budgetaccount.model.BudgetAccount;
import ljakovic.simplebudgeting.budgetaccount.repo.BudgetAccountRepo;
import ljakovic.simplebudgeting.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BudgetAccountService {

    @Autowired
    private BudgetAccountRepo repo;
    @Autowired
    private BudgetAccountMapper mapper;
    @Autowired
    private UserUtil userUtil;

    public BudgetAccountDto getById(UUID uuid) {
        BudgetAccount budgetAccount = repo.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Budget account not found"));

        return mapper.mapTo(budgetAccount);
    }

    public List<BudgetAccountDto> getUserAccounts() {
        final UUID loggedInUserId = UUID.fromString(userUtil.getLoggedInUserUsername());

        return repo.findByUserId(loggedInUserId).stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    public BudgetAccountDto getAccountByIdUserId(UUID id, UUID userId) {
        return mapper.mapTo(repo.findByIdUserId(id, userId));
    }
}
