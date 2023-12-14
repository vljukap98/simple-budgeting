package ljakovic.simplebudgeting.budgetaccount.service;

import jakarta.persistence.EntityNotFoundException;
import ljakovic.simplebudgeting.budgetaccount.dto.BudgetAccountDto;
import ljakovic.simplebudgeting.budgetaccount.mapper.BudgetAccountMapper;
import ljakovic.simplebudgeting.budgetaccount.model.BudgetAccount;
import ljakovic.simplebudgeting.budgetaccount.repo.BudgetAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BudgetAccountService {

    @Autowired
    private BudgetAccountRepo repo;
    @Autowired
    private BudgetAccountMapper mapper;
    public BudgetAccountDto getById(UUID uuid) {
        BudgetAccount budgetAccount = repo.findById(uuid)
                .orElseThrow(() -> new EntityNotFoundException("Budget account not found"));

        return mapper.mapTo(budgetAccount);
    }
}
