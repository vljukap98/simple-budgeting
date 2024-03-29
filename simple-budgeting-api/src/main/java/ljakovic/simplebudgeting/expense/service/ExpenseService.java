package ljakovic.simplebudgeting.expense.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Tuple;
import ljakovic.simplebudgeting.aggregation.dto.AggregationResDto;
import ljakovic.simplebudgeting.aggregation.dto.AggregationTypeEnum;
import ljakovic.simplebudgeting.appuser.service.AppUserService;
import ljakovic.simplebudgeting.budgetaccount.model.BudgetAccount;
import ljakovic.simplebudgeting.budgetaccount.repo.BudgetAccountRepo;
import ljakovic.simplebudgeting.category.model.Category;
import ljakovic.simplebudgeting.category.repo.CategoryRepo;
import ljakovic.simplebudgeting.expense.dto.ExpenseDto;
import ljakovic.simplebudgeting.expense.dto.ExpenseSearchDto;
import ljakovic.simplebudgeting.expense.mapper.ExpenseMapper;
import ljakovic.simplebudgeting.expense.model.Expense;
import ljakovic.simplebudgeting.expense.repo.ExpenseRepo;
import ljakovic.simplebudgeting.expense.repo.impl.dto.ExpenseQueryRepoDto;
import ljakovic.simplebudgeting.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepo expenseRepo;

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private BudgetAccountRepo accountRepo;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private ExpenseMapper mapper;

    @Autowired
    private UserUtil util;

    public List<ExpenseDto> getByBudgetAccountIdPageable(Pageable pageable, Integer budgetAccountId) {
        final BudgetAccount account = accountRepo.findById(budgetAccountId)
                .orElseThrow(() -> new EntityNotFoundException("Budget account not found"));

        final Page<Expense> expenses = expenseRepo.findByAccount(account, pageable);

        return expenses.getContent().stream()
                .map(mapper::mapTo)
                .toList();
    }
    public List<ExpenseDto> searchByBudgetAccountIdPageable(Pageable pageable, Integer budgedAccountId, ExpenseSearchDto searchDto) {
        ExpenseQueryRepoDto dto = ExpenseQueryRepoDto.builder()
                .budgedAccountId(budgedAccountId.toString())
                .amountMin(searchDto.getAmountMin())
                .amountMax(searchDto.getAmountMax())
                .startDate(searchDto.getStartDate())
                .endDate(searchDto.getEndDate())
                .categoryNames(searchDto.getCategoryNames())
                .categoryTypes(searchDto.getCategoryTypes())
                .build();


        final List<Integer> expenseIds = expenseRepo.searchExpensesByAccount(dto, pageable);

        return expenseRepo.findAllById(expenseIds).stream()
                .map(mapper::mapTo)
                .toList();
    }

    public List<ExpenseDto> getByBudgetAccountId(Integer budgetAccountId) {
        final List<Expense> expenses = expenseRepo.findByBudgetAccountId(budgetAccountId);

        return expenses.stream()
                .map(mapper::mapTo)
                .toList();
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
        final BudgetAccount account = accountRepo.findById(dto.getAccount().getId())
                .orElseThrow(() -> new EntityNotFoundException("Account not found"));

        if (!accountBelongsToLoggedInUser(account)) {
            throw new UnsupportedOperationException("Unable to edit expense");
        }

        Expense expense = expenseRepo.findById(dto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Expense not found"));

        final Category category = categoryRepo.findById(dto.getCategory().getId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        account.setTotalResources(account.getTotalResources() - expense.getAmount());

        expense.setAmount(dto.getAmount());
        expense.setCategory(category);
        expense.setAccount(account);

        expenseRepo.save(expense);

        account.setTotalResources(account.getTotalResources() + expense.getAmount());

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

    public List<AggregationResDto> getExpensesAggregated(
            Integer id,
            String startDate,
            String endDate,
            AggregationTypeEnum aggregationType) {

        List<Tuple> expenses = expenseRepo.aggregate(id, startDate, endDate, aggregationType);

        List<AggregationResDto> resDto = new ArrayList<>();

        if (expenses != null && !expenses.isEmpty()) {
            for (Tuple t : expenses) {
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

    private boolean accountBelongsToLoggedInUser(BudgetAccount account) {
        List<BudgetAccount> accounts = accountRepo.findByUserId(util.getLoggedInUserId());

        return accounts.contains(account);
    }

}
