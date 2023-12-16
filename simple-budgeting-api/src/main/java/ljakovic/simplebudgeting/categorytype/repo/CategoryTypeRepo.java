package ljakovic.simplebudgeting.categorytype.repo;

import ljakovic.simplebudgeting.categorytype.model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CategoryTypeRepo extends JpaRepository<CategoryType, UUID> {
}
