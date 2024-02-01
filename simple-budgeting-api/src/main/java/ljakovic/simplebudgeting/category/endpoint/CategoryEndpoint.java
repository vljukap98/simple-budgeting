package ljakovic.simplebudgeting.category.endpoint;

import ljakovic.simplebudgeting.category.dto.CategoryDto;
import ljakovic.simplebudgeting.category.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/category")
public class CategoryEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryEndpoint.class);

    @Autowired
    private CategoryService service;

    @GetMapping("/all")
    public ResponseEntity<List<CategoryDto>> getAll() {
        LOGGER.info("GET request /v1/category/all");
        return ResponseEntity.ok(service.getCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getById(@PathVariable Integer id) {
        LOGGER.info("GET request /v1/category/{}", id);
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto dto) {
        LOGGER.info("POST request /v1/category/add");
        LOGGER.info("POST request body: {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @PutMapping("/update")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto dto) {
        LOGGER.info("POST request /v1/category/update");
        LOGGER.info("POST request body: {}", dto);
        return ResponseEntity.ok(service.create(dto));
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCategory(@PathVariable Integer id) {
        LOGGER.info("DELETE request /v1/category/delete/{}", id);
        service.delete(id);
    }
}
