package ljakovic.simplebudgeting.income.repo.impl.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
@Builder
public class IncomeQueryRepoDto {

    private String budgedAccountId;
    private Double amountMin;
    private Double amountMax;
    private Date startDate;
    private Date endDate;
}
