package ljakovic.simplebudgeting.aggregation.service;

import ljakovic.simplebudgeting.aggregation.dto.AggregationDto;
import ljakovic.simplebudgeting.appuser.service.AppUserService;
import ljakovic.simplebudgeting.budgetaccount.service.BudgetAccountService;
import ljakovic.simplebudgeting.expense.dto.ExpenseDto;
import ljakovic.simplebudgeting.expense.service.ExpenseService;
import ljakovic.simplebudgeting.income.dto.IncomeDto;
import ljakovic.simplebudgeting.income.service.IncomeService;
import ljakovic.simplebudgeting.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AggregationService {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private AppUserService userService;

    @Autowired
    private BudgetAccountService budgetAccountService;

    @Autowired
    private UserUtil userUtil;

    public List<ExpenseDto> aggregateMoneySpent(Pageable pageable, UUID id, AggregationDto dto) {
        List<ExpenseDto> expensesAggregated = new ArrayList<>();

        switch (dto.getAggregationType()) {
            case YEARLY -> expensesAggregated = getExpensesYearly(pageable, id, dto);
            case MONTHLY -> expensesAggregated = getExpensesMonthly(pageable, id, dto);
        }
        return expensesAggregated;
    }

    public List<IncomeDto> aggregateMoneyEarned(Pageable pageable, UUID id, AggregationDto dto) {
        List<IncomeDto> incomesAggregated = new ArrayList<>();

        switch (dto.getAggregationType()) {
            case YEARLY -> incomesAggregated = getIncomesYearly(pageable, id, dto);
            case MONTHLY -> incomesAggregated = getIncomesMonthly(pageable, id, dto);
        }

        return incomesAggregated;
    }

    private List<ExpenseDto> getExpensesMonthly(Pageable pageable, UUID id, AggregationDto dto) {
        final LocalDate today = LocalDate.now();
        final LocalDate aggregatePeriod = today.minusMonths(dto.getAggregationTime());

        String todayDate = formatLocalDate(today);
        String aggregatePeriodDate = formatLocalDate(aggregatePeriod);

        return expenseService
                .getExpensesAggregatedMonthly(pageable, id, aggregatePeriodDate, todayDate);
    }

    private List<ExpenseDto> getExpensesYearly(Pageable pageable, UUID id, AggregationDto dto) {
        final LocalDate today = LocalDate.now();
        final LocalDate aggregatePeriod = today.minusYears(dto.getAggregationTime());

        String todayDate = formatLocalDate(today);
        String aggregatePeriodDate = formatLocalDate(aggregatePeriod);

        return expenseService
                .getExpensesAggregatedYearly(pageable, id, aggregatePeriodDate, todayDate);
    }

    private List<IncomeDto> getIncomesMonthly(Pageable pageable, UUID id, AggregationDto dto) {
        final LocalDate today = LocalDate.now();
        final LocalDate aggregatePeriod = today.minusMonths(dto.getAggregationTime());

        String todayDate = formatLocalDate(today);
        String aggregatePeriodDate = formatLocalDate(aggregatePeriod);

        return incomeService
                .getIncomesAggregatedMonthly(pageable, id, aggregatePeriodDate, todayDate);
    }

    private List<IncomeDto> getIncomesYearly(Pageable pageable, UUID id, AggregationDto dto) {
        final LocalDate today = LocalDate.now();
        final LocalDate aggregatePeriod = today.minusYears(dto.getAggregationTime());

        String todayDate = formatLocalDate(today);
        String aggregatePeriodDate = formatLocalDate(aggregatePeriod);

        return incomeService
                .getIncomesAggregatedYearly(pageable, id, aggregatePeriodDate, todayDate);
    }

    public static String formatLocalDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return localDate.format(formatter);
    }
}
