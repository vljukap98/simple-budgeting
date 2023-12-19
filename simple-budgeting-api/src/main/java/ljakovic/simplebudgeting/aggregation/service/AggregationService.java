package ljakovic.simplebudgeting.aggregation.service;

import ljakovic.simplebudgeting.aggregation.dto.AggregationDto;
import ljakovic.simplebudgeting.aggregation.dto.AggregationResDto;
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
import java.util.Date;
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

    public List<AggregationResDto> aggregateMoneySpent(Integer id, AggregationDto dto) {
        List<AggregationResDto> expensesAggregated = new ArrayList<>();

        switch (dto.getAggregationType()) {
            case YEARLY -> expensesAggregated = getExpensesYearly(id, dto);
            case MONTHLY -> expensesAggregated = getExpensesMonthly(id, dto);
        }
        return expensesAggregated;
    }

    public List<AggregationResDto> aggregateMoneyEarned(Integer id, AggregationDto dto) {
        List<AggregationResDto> incomesAggregated = new ArrayList<>();

        switch (dto.getAggregationType()) {
            case YEARLY -> incomesAggregated = getIncomesYearly(id, dto);
            case MONTHLY -> incomesAggregated = getIncomesMonthly(id, dto);
        }

        return incomesAggregated;
    }

    private List<AggregationResDto> getExpensesMonthly(Integer id, AggregationDto dto) {
        final LocalDate today = LocalDate.now();
        final LocalDate aggregatePeriod = today.withDayOfMonth(1)
                .minusMonths(dto.getAggregationTime());

        String todayDate = formatLocalDate(today);
        String aggregatePeriodDate = formatLocalDate(aggregatePeriod);

        return expenseService
                .getExpensesAggregatedMonthly(id, aggregatePeriodDate, todayDate);
    }

    private List<AggregationResDto> getExpensesYearly(Integer id, AggregationDto dto) {
        final LocalDate today = LocalDate.now();
        final LocalDate aggregatePeriod = today.minusYears(dto.getAggregationTime());

        String todayDate = formatLocalDate(today);
        String aggregatePeriodDate = formatLocalDate(aggregatePeriod);

        return expenseService
                .getExpensesAggregatedYearly(id, aggregatePeriodDate, todayDate);
    }

    private List<AggregationResDto> getIncomesMonthly(Integer id, AggregationDto dto) {
        final LocalDate today = LocalDate.now();
        final LocalDate aggregatePeriod = today.withDayOfMonth(1)
                .minusMonths(dto.getAggregationTime());

        String todayDate = formatLocalDate(today);
        String aggregatePeriodDate = formatLocalDate(aggregatePeriod);

        return incomeService
                .getIncomesAggregatedMonthly(id, aggregatePeriodDate, todayDate);
    }

    private List<AggregationResDto> getIncomesYearly(Integer id, AggregationDto dto) {
        final LocalDate today = LocalDate.now();
        final LocalDate aggregatePeriod = today.minusYears(dto.getAggregationTime());

        String todayDate = formatLocalDate(today);
        String aggregatePeriodDate = formatLocalDate(aggregatePeriod);

        return incomeService
                .getIncomesAggregatedYearly(id, aggregatePeriodDate, todayDate);
    }

    public static String formatLocalDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return localDate.format(formatter);
    }

    private static Date convertLocalDateToDate(LocalDate localDate) {
        // Get the number of days between the epoch day and the specified LocalDate
        long epochDay = localDate.toEpochDay();

        // Create a Date object using the obtained value
        return new Date(epochDay * 24 * 60 * 60 * 1000); // Convert days to milliseconds
    }
}
