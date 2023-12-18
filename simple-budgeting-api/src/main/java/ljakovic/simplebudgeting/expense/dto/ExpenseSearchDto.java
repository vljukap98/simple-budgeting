package ljakovic.simplebudgeting.expense.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExpenseSearchDto {
    private Double amountMin;
    private Double amountMax;
    private Date startDate;
    private Date endDate;
    private List<String> categoryNames;
    private List<String> categoryTypes;
}
