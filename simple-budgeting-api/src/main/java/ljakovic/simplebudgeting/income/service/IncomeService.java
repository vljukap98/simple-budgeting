package ljakovic.simplebudgeting.income.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import ljakovic.simplebudgeting.aggregation.dto.AggregationResDto;
import ljakovic.simplebudgeting.aggregation.dto.AggregationTypeEnum;
import ljakovic.simplebudgeting.budgetaccount.model.BudgetAccount;
import ljakovic.simplebudgeting.budgetaccount.repo.BudgetAccountRepo;
import ljakovic.simplebudgeting.income.dto.IncomeDto;
import ljakovic.simplebudgeting.income.dto.IncomeSearchDto;
import ljakovic.simplebudgeting.income.mapper.IncomeMapper;
import ljakovic.simplebudgeting.income.model.Income;
import ljakovic.simplebudgeting.income.repo.IncomeRepo;
import ljakovic.simplebudgeting.income.repo.impl.dto.IncomeQueryRepoDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private BudgetAccountRepo accountRepo;

    @Autowired
    private IncomeRepo incomeRepo;

    @Autowired
    private IncomeMapper mapper;

    public List<IncomeDto> searchByBudgetAccountIdPageable(Pageable pageable, Integer budgetAccountId, IncomeSearchDto searchDto) {

        IncomeQueryRepoDto dto = IncomeQueryRepoDto.builder()
                .budgedAccountId(budgetAccountId.toString())
                .amountMin(searchDto.getAmountMin())
                .amountMax(searchDto.getAmountMax())
                .startDate(searchDto.getStartDate())
                .endDate(searchDto.getEndDate())
                .build();

        final List<Integer> incomeIds = incomeRepo.searchIncomeByAccount(dto, pageable);

        return incomeRepo.findAllById(incomeIds)
                .stream()
                .map(mapper::mapTo)
                .toList();
    }

    public IncomeDto createIncome(IncomeDto dto) {
        final BudgetAccount account = accountRepo.findById(dto.getAccount().getId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        final Income income = Income.builder()
                .amount(dto.getAmount())
                .dateCreated(new Date())
                .account(account)
                .build();

        incomeRepo.save(income);
        final Double accountResources = account.getTotalResources();
        account.setTotalResources(accountResources + income.getAmount());
        accountRepo.save(account);
        return mapper.mapTo(income);
    }

    public void delete(Integer id) {
        final Income income = incomeRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Income entity not found"));

        final BudgetAccount account = income.getAccount();
        account.setTotalResources(account.getTotalResources() - income.getAmount());
        incomeRepo.deleteById(id);
        accountRepo.save(account);
    }

    public List<AggregationResDto> getIncomesAggregated(
            Integer id,
            String startDate,
            String endDate,
            AggregationTypeEnum aggregationType) {
        List<Tuple> incomes = incomeRepo.aggregate(id, startDate, endDate, aggregationType);

        List<AggregationResDto> resDto = new ArrayList<>();

        if (incomes != null && !incomes.isEmpty()) {
            for (Tuple t : incomes) {
                BigDecimal year = t.get("year", BigDecimal.class);
                Double expenseAmount = t.get("total_amount", Double.class);

                AggregationResDto dto = AggregationResDto.builder()
                        .amount(expenseAmount)
                        .build();

                if (aggregationType.equals(AggregationTypeEnum.MONTHLY)) {
                    BigDecimal month = t.get("month", BigDecimal.class);
                    dto.setTimePeriod(Month.of(month.intValue()).toString() + " " + year.toString());
                } else {
                    dto.setTimePeriod(year.toString());
                }

                resDto.add(dto);
            }
        }

        return resDto;
    }
}
