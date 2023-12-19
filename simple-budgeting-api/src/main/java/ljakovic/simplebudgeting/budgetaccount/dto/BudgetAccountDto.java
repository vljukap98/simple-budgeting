package ljakovic.simplebudgeting.budgetaccount.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ljakovic.simplebudgeting.appuser.dto.AppUserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BudgetAccountDto {

    private Integer id;
    private Double resources;
    private Date dateCreated;
    private Date dateModified;
    private AppUserDto user;
}
