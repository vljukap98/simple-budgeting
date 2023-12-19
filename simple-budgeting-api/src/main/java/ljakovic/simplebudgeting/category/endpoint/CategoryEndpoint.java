package ljakovic.simplebudgeting.category.endpoint;

import ljakovic.simplebudgeting.category.dto.CategoryDto;
import ljakovic.simplebudgeting.category.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/category")
public class CategoryEndpoint {

    @Autowired
    private CategoryService service;

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAll() {
        return ResponseEntity.ok(service.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        service.delete(id);
    }
}
