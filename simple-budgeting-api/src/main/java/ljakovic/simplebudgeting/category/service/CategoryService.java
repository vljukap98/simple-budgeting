package ljakovic.simplebudgeting.category.service;

import jakarta.persistence.EntityNotFoundException;
import ljakovic.simplebudgeting.category.dto.CategoryDto;
import ljakovic.simplebudgeting.category.mapper.CategoryMapper;
import ljakovic.simplebudgeting.category.model.Category;
import ljakovic.simplebudgeting.category.repo.CategoryRepo;
import ljakovic.simplebudgeting.categorytype.model.CategoryType;
import ljakovic.simplebudgeting.categorytype.repo.CategoryTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private CategoryTypeRepo categoryTypeRepo;

    @Autowired
    CategoryMapper mapper;

    public List<CategoryDto> getCategories() {
        return categoryRepo.findAll().stream()
                .map(mapper::mapTo)
                .toList();
    }

    public CategoryDto getById(Integer id) {
        final Category category = categoryRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return mapper.mapTo(category);
    }

    public CategoryDto create(CategoryDto dto) {
        final CategoryType categoryType = categoryTypeRepo.findById(dto.getCategoryType().getId())
                .orElseThrow(() -> new EntityNotFoundException("Category type not found"));
        final Category category = Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .dateCreated(new Date())
                .dateModified(new Date())
                .categoryType(categoryType)
                .build();

        categoryRepo.save(category);

        return mapper.mapTo(category);
    }

    public CategoryDto update(CategoryDto dto) {
        final Category category = categoryRepo.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Category not found"));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setDateModified(new Date());

        categoryRepo.save(category);

        return mapper.mapTo(category);
    }

    public void delete(Integer id) {
        categoryRepo.deleteById(id);
    }
}
