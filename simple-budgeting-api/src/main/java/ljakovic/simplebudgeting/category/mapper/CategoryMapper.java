package ljakovic.simplebudgeting.category.mapper;

import ljakovic.simplebudgeting.category.dto.CategoryDto;
import ljakovic.simplebudgeting.category.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDto mapTo(Category category) {
        return CategoryDto.builder()
                .id(category.getId().toString())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
