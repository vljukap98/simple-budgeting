package ljakovic.simplebudgeting.expense.repo.impl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExpenseQueryRepoDto {

    private String budgedAccountId;
    private Double amountMin;
    private Double amountMax;
    private Date startDate;
    private Date endDate;
    private List<String> categoryNames;
    private List<String> categoryTypes;
}
