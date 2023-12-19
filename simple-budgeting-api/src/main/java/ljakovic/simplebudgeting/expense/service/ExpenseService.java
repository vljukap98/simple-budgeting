package ljakovic.simplebudgeting.expense.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import ljakovic.simplebudgeting.aggregation.dto.AggregationResDto;
import ljakovic.simplebudgeting.budgetaccount.model.BudgetAccount;
import ljakovic.simplebudgeting.budgetaccount.repo.BudgetAccountRepo;
import ljakovic.simplebudgeting.category.model.Category;
import ljakovic.simplebudgeting.category.repo.CategoryRepo;
import ljakovic.simplebudgeting.expense.dto.ExpenseDto;
import ljakovic.simplebudgeting.expense.dto.ExpenseSearchDto;
import ljakovic.simplebudgeting.expense.mapper.ExpenseMapper;
import ljakovic.simplebudgeting.expense.model.Expense;
import ljakovic.simplebudgeting.expense.repo.ExpenseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepo expenseRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private BudgetAccountRepo accountRepo;

    @Autowired
    private ExpenseMapper mapper;

    public List<ExpenseDto> getByBudgetAccountIdPageable(Pageable pageable, Integer budgetAccountId) {
        final BudgetAccount account = accountRepo.findById(budgetAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Budget account not found"));

        final Page<Expense> expenses = expenseRepo.findByAccount(account, pageable);

        return expenses.getContent().stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }
    public List<ExpenseDto> searchByBudgetAccountIdPageable(Pageable pageable, Integer budgedAccountId, ExpenseSearchDto searchDto) {
        final BudgetAccount account = accountRepo.findById(budgedAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Budget account not found"));

        String accountId = budgedAccountId.toString();
        Double amountMin = searchDto.getAmountMin();
        Double amountMax = searchDto.getAmountMax();
        Date startDate = searchDto.getStartDate();
        Date endDate = searchDto.getEndDate();
        List<String> categoryNames = searchDto.getCategoryNames();
        List<String> categoryTypes = searchDto.getCategoryTypes();


        final List<Integer> expenseIds = expenseRepo.searchExpensesByAccount(
                accountId,
                amountMin,
                amountMax,
                startDate,
                endDate,
                categoryNames,
                categoryTypes,
                pageable
        );

        return expenseRepo.findAllById(expenseIds).stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    public List<ExpenseDto> getByBudgetAccountId(Integer budgetAccountId) {
        final List<Expense> expenses = expenseRepo.findByBudgetAccountId(budgetAccountId);

        return expenses.stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    public ExpenseDto getById(Integer id) {
        final Expense expense = expenseRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        return mapper.mapTo(expense);
    }

    public ExpenseDto createExpense(ExpenseDto dto) {
        final Category category = categoryRepo.findById(dto.getCategory().getId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        final BudgetAccount account = accountRepo.findById(dto.getAccount().getId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        final Expense expense = Expense.builder()
                .amount(dto.getAmount())
                .dateCreated(new Date())
                .account(account)
                .category(category)
                .build();

        expenseRepo.save(expense);

        final Double accountResources = account.getTotalResources();

        account.setTotalResources(accountResources - expense.getAmount());

        accountRepo.save(account);

        return mapper.mapTo(expense);
    }

    public ExpenseDto update(ExpenseDto dto) {
        Expense expense = expenseRepo.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        final Category category = categoryRepo.findById(dto.getCategory().getId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        final BudgetAccount account = accountRepo.findById(dto.getAccount().getId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        expense.setAmount(dto.getAmount());
        expense.setCategory(category);
        expense.setAccount(account);

        expenseRepo.save(expense);

        return mapper.mapTo(expense);
    }

    public void delete(Integer id) {
        final Expense expense = expenseRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        final BudgetAccount account = expense.getAccount();
        account.setTotalResources(account.getTotalResources() + expense.getAmount());
        expenseRepo.deleteById(id);
        accountRepo.save(account);
    }

    public List<AggregationResDto> getExpensesAggregatedMonthly(Integer id, String startDate, String endDate) {
        List<Tuple> expenses = null;

        try {
            expenses = expenseRepo.aggregateMonthly(id, convert(startDate), convert(endDate));
        } catch (Exception e) {
            //
        }

        List<AggregationResDto> resDto = new ArrayList<>();

        if (expenses != null && !expenses.isEmpty()) {
            for (Tuple t : expenses) {
                Integer month = t.get("month", Integer.class);
                Integer year = t.get("year", Integer.class);
                Double expenseAmount = t.get("total_amount", Double.class);

                AggregationResDto dto = AggregationResDto.builder()
                        .timePeriod(Month.of(month).toString() + " " + year.toString())
                        .amount(expenseAmount)
                        .build();

                resDto.add(dto);
            }
        }

        return resDto;
    }

    public List<AggregationResDto> getExpensesAggregatedYearly(Integer id, String startDate, String endDate) {
        List<Tuple> expenses = null;

        try {
            expenses = expenseRepo.aggregateYearly(id, convert(startDate), convert(endDate));
        } catch (Exception e) {
            //
        }

        List<AggregationResDto> resDto = new ArrayList<>();

        if (expenses != null && !expenses.isEmpty()) {
            for (Tuple t : expenses) {
                Integer year = t.get("year", Integer.class);
                Double expenseAmount = t.get("total_amount", Double.class);

                AggregationResDto dto = AggregationResDto.builder()
                        .timePeriod(year.toString())
                        .amount(expenseAmount)
                        .build();

                resDto.add(dto);
            }
        }

        return resDto;
    }

    private Date convert(String date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.parse(date);
    }

}
