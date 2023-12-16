package ljakovic.simplebudgeting.category.mapper;

import ljakovic.simplebudgeting.category.dto.CategoryDto;
import ljakovic.simplebudgeting.category.model.Category;
import ljakovic.simplebudgeting.categorytype.CategoryTypeDto;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDto mapTo(Category category) {
        final CategoryTypeDto categoryType = CategoryTypeDto.builder()
                .id(category.getCategoryType().getId().toString())
                .name(category.getCategoryType().getName())
                .build();

        return CategoryDto.builder()
                .id(category.getId().toString())
                .name(category.getName())
                .description(category.getDescription())
                .categoryType(categoryType)
                .build();
    }
}
