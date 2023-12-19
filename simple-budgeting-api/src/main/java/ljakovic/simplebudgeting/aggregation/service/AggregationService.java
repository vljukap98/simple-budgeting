package ljakovic.simplebudgeting.aggregation.service;

import ljakovic.simplebudgeting.aggregation.dto.AggregationDto;
import ljakovic.simplebudgeting.appuser.service.AppUserService;
import ljakovic.simplebudgeting.budgetaccount.service.BudgetAccountService;
import ljakovic.simplebudgeting.expense.dto.ExpenseDto;
import ljakovic.simplebudgeting.expense.service.ExpenseService;
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
    private AppUserService userService;

    @Autowired
    private BudgetAccountService budgetAccountService;

    @Autowired
    private UserUtil userUtil;

    public List<ExpenseDto> aggregateMoneySpent(Pageable pageable, UUID id, AggregationDto dto) {
        List<ExpenseDto> expensesAggregated = new ArrayList<>();

        final LocalDate today = LocalDate.now();
        final LocalDate aggregatePeriod = today.minusMonths(dto.getAggregationTime());

        String todayDate = formatLocalDate(today);
        String aggregatePeriodDate = formatLocalDate(aggregatePeriod);

        switch (dto.getAggregationType()) {
            case YEARLY -> expensesAggregated = expenseService
                    .getExpensesAggregatedYearly(pageable, id, aggregatePeriodDate, todayDate);
            case MONTHLY -> expensesAggregated = expenseService
                    .getExpensesAggregatedMonthly(pageable, id, aggregatePeriodDate, todayDate);
        }

        return expensesAggregated;
    }

    public static String formatLocalDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return localDate.format(formatter);
    }
}
