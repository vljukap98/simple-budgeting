package ljakovic.simplebudgeting.category.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import ljakovic.simplebudgeting.categorytype.CategoryTypeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryDto {

    private Integer id;
    private String name;
    private String description;
    private CategoryTypeDto categoryType;
}
