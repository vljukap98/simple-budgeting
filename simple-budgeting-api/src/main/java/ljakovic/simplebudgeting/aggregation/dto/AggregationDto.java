package ljakovic.simplebudgeting.aggregation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ljakovic.simplebudgeting.expense.dto.ExpenseSearchDto;
import ljakovic.simplebudgeting.income.dto.IncomeSearchDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AggregationDto {

    private AggregationTypeEnum aggregationType;
    private Integer aggregationTime;
}
