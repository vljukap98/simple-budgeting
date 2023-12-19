package ljakovic.simplebudgeting.income.repo;

import jakarta.persistence.Tuple;
import ljakovic.simplebudgeting.income.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface IncomeRepo extends JpaRepository<Income, UUID>, IncomeSearchRepo, IncomeAggregatedRepo {

    @Query("SELECT i FROM Income i WHERE i.account.id = ?1")
    List<Income> findByAccountId(UUID id);
}
