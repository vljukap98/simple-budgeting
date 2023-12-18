package ljakovic.simplebudgeting.categorytype.repo;

import ljakovic.simplebudgeting.categorytype.model.CategoryType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryTypeRepoTest {

    @Autowired
    private CategoryTypeRepo repo;
    @Test
    void findByName() {
        //given
        String name = "Necessity";
        String description = "Monthly necessities";

        CategoryType categoryType = new CategoryType();
        categoryType.setName(name);
        categoryType.setDescription(description);
        categoryType.setDateCreated(new Date());
        categoryType.setDateModified(new Date());

        repo.save(categoryType);
        //when
        CategoryType categoryTypeFound = repo.findByName(name).orElseThrow();
        //then
        assertNotNull(categoryTypeFound);
    }
}