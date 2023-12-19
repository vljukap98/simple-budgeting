package ljakovic.simplebudgeting.income.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import ljakovic.simplebudgeting.budgetaccount.model.BudgetAccount;
import ljakovic.simplebudgeting.budgetaccount.repo.BudgetAccountRepo;
import ljakovic.simplebudgeting.expense.dto.ExpenseDto;
import ljakovic.simplebudgeting.income.dto.IncomeDto;
import ljakovic.simplebudgeting.income.dto.IncomeSearchDto;
import ljakovic.simplebudgeting.income.mapper.IncomeMapper;
import ljakovic.simplebudgeting.income.model.Income;
import ljakovic.simplebudgeting.income.repo.IncomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class IncomeService {

    @Autowired
    private BudgetAccountRepo accountRepo;

    @Autowired
    private IncomeRepo incomeRepo;

    @Autowired
    private IncomeMapper mapper;

    public List<IncomeDto> searchByBudgetAccountIdPageable(Pageable pageable, UUID budgetAccountId, IncomeSearchDto searchDto) {
        final BudgetAccount account = accountRepo.findById(budgetAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Budget account not found"));

        String accountId = budgetAccountId.toString();
        Double amountMin = searchDto.getAmountMin();
        Double amountMax = searchDto.getAmountMax();
        Date startDate = searchDto.getStartDate();
        Date endDate = searchDto.getEndDate();

        final List<String> incomeIds = incomeRepo.searchIncomeByAccount(
                accountId,
                amountMin,
                amountMax,
                startDate,
                endDate,
                pageable
        );

        return incomeRepo.findAllById(incomeIds.stream().map(UUID::fromString).collect(Collectors.toList()))
                .stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    public IncomeDto createIncome(IncomeDto dto) {
        final BudgetAccount account = accountRepo.findById(UUID.fromString(dto.getAccount().getId()))
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        final Income income = Income.builder()
                .amount(Double.parseDouble(dto.getAmount()))
                .dateCreated(new Date())
                .account(account)
                .build();

        incomeRepo.save(income);
        final Double accountResources = account.getTotalResources();
        account.setTotalResources(accountResources + income.getAmount());
        accountRepo.save(account);
        return mapper.mapTo(income);
    }

    public void delete(UUID id) {
        final Income income = incomeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Income entity not found"));

        final BudgetAccount account = income.getAccount();
        account.setTotalResources(account.getTotalResources() - income.getAmount());
        incomeRepo.deleteById(id);
        accountRepo.save(account);
    }

    public List<IncomeDto> getIncomesAggregatedYearly(Pageable pageable, UUID id, String startDate, String endDate) {
        List<Tuple> incomes = null;

        try {
            incomes = incomeRepo.aggregateYearly(id, convert(startDate), convert(endDate));
        } catch (Exception e) {
            //
        }

        List<IncomeDto> incomesDto = new ArrayList<>();

        if (incomes != null && !incomes.isEmpty()) {
            for (Tuple t : incomes) {
                Integer year = t.get("year", Integer.class);
                Double expenseAmount = t.get("total_amount", Double.class);

                IncomeDto dto = IncomeDto.builder()
                        .dateCreated(year.toString())
                        .amount(expenseAmount.toString())
                        .build();

                incomesDto.add(dto);
            }
        }

        return incomesDto;
    }

    public List<IncomeDto> getIncomesAggregatedMonthly(Pageable pageable, UUID id, String startDate, String endDate) {
        List<Tuple> incomes = null;

        try {
            incomes = incomeRepo.aggregateMonthly(id, convert(startDate), convert(endDate));
        } catch (Exception e) {
            //
        }

        List<IncomeDto> incomesDto = new ArrayList<>();

        if (incomes != null && !incomes.isEmpty()) {
            for (Tuple t : incomes) {
                Integer month = t.get("month", Integer.class);
                Integer year = t.get("year", Integer.class);
                Double expenseAmount = t.get("total_amount", Double.class);

                IncomeDto dto = IncomeDto.builder()
                        .dateCreated(Month.of(month).toString() + " " + year.toString())
                        .amount(expenseAmount.toString())
                        .build();

                incomesDto.add(dto);
            }
        }

        return incomesDto;
    }

    private Date convert(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(date);
    }
}
