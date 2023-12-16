package ljakovic.simplebudgeting.category.service;

import jakarta.persistence.EntityNotFoundException;
import ljakovic.simplebudgeting.category.dto.CategoryDto;
import ljakovic.simplebudgeting.category.mapper.CategoryMapper;
import ljakovic.simplebudgeting.category.model.Category;
import ljakovic.simplebudgeting.category.repo.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepo repo;

    @Autowired
    CategoryMapper mapper;

    public List<CategoryDto> getCategories() {
        return repo.findAll().stream()
                .map(mapper::mapTo)
                .collect(Collectors.toList());
    }

    public CategoryDto getById(UUID id) {
        final Category category = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("Category not found"));
        return mapper.mapTo(category);
    }

    public CategoryDto create(CategoryDto dto) {
        final Category category = Category.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .dateCreated(new Date())
                .dateModified(new Date())
                .build();

        repo.save(category);

        return mapper.mapTo(category);
    }

    public CategoryDto update(CategoryDto dto) {
        final Category category = repo.findById(UUID.fromString(dto.getId())).orElseThrow(() -> new EntityNotFoundException("Category not found"));

        category.setName(dto.getName());
        category.setDescription(dto.getDescription());
        category.setDateModified(new Date());

        repo.save(category);

        return mapper.mapTo(category);
    }

    public void delete(UUID id) {
        repo.deleteById(id);
    }
}
