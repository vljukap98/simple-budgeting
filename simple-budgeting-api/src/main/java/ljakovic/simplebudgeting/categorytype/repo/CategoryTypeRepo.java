package ljakovic.simplebudgeting.categorytype.repo;

import ljakovic.simplebudgeting.categorytype.model.CategoryType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryTypeRepo extends JpaRepository<CategoryType, Integer> {

    Optional<CategoryType> findByName(String name);
}
