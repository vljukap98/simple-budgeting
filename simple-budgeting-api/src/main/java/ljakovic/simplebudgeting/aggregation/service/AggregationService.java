package ljakovic.simplebudgeting.aggregation.service;

import ljakovic.simplebudgeting.aggregation.dto.AggregationDto;
import ljakovic.simplebudgeting.aggregation.dto.AggregationResDto;
import ljakovic.simplebudgeting.aggregation.dto.AggregationTypeEnum;
import ljakovic.simplebudgeting.appuser.service.AppUserService;
import ljakovic.simplebudgeting.budgetaccount.service.BudgetAccountService;
import ljakovic.simplebudgeting.expense.service.ExpenseService;
import ljakovic.simplebudgeting.income.service.IncomeService;
import ljakovic.simplebudgeting.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
        LocalDate today = LocalDate.now();
        LocalDate aggregatePeriod = null;
        List<AggregationResDto> aggregatedIncomes = new ArrayList<>();

        if (dto.getAggregationType().equals(AggregationTypeEnum.MONTHLY)) {
            aggregatePeriod = today.withDayOfMonth(1)
                    .minusMonths(dto.getAggregationTime() - 1);
        } else if (dto.getAggregationType().equals(AggregationTypeEnum.YEARLY)) {
            aggregatePeriod = today.minusYears(dto.getAggregationTime());
        }

        String todayDate = formatLocalDate(today);
        String aggregatePeriodDate = formatLocalDate(aggregatePeriod);

        aggregatedIncomes = expenseService
                .getExpensesAggregated(id, aggregatePeriodDate, todayDate, dto.getAggregationType());

        return aggregatedIncomes;
    }

    public List<AggregationResDto> aggregateMoneyEarned(Integer id, AggregationDto dto) {
        LocalDate today = LocalDate.now();
        LocalDate aggregatePeriod = null;
        List<AggregationResDto> aggregatedIncomes = new ArrayList<>();

        if (dto.getAggregationType().equals(AggregationTypeEnum.MONTHLY)) {
            aggregatePeriod = today.withDayOfMonth(1)
                    .minusMonths(dto.getAggregationTime() - 1);
        } else if (dto.getAggregationType().equals(AggregationTypeEnum.YEARLY)) {
            aggregatePeriod = today.minusYears(dto.getAggregationTime());
        }

        String todayDate = formatLocalDate(today);
        String aggregatePeriodDate = formatLocalDate(aggregatePeriod);

        aggregatedIncomes = incomeService
                .getIncomesAggregated(id, aggregatePeriodDate, todayDate, dto.getAggregationType());

        return aggregatedIncomes;
    }

    public static String formatLocalDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return localDate.format(formatter);
    }
}
