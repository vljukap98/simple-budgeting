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
public interface IncomeRepo extends JpaRepository<Income, UUID>, IncomeSearchRepo {

    @Query("SELECT i FROM Income i WHERE i.account.id = ?1")
    List<Income> findByAccountId(UUID id);


    @Query("SELECT " +
            "MONTH(i.dateCreated) AS month, " +
            "YEAR(i.dateCreated) AS year, " +
            "SUM(i.amount) AS total_amount " +
            "FROM Income i " +
            "WHERE i.dateCreated >= :startDate " +
            "AND i.dateCreated <= :endDate " +
            "AND i.account.id = :accountId " +
            "GROUP BY month, year")
    List<Tuple> aggregateMonthly(@Param("accountId") UUID accountId,
                                @Param("startDate") Date startDate,
                                @Param("endDate") Date endDate);

    @Query("SELECT " +
            "YEAR(i.dateCreated) AS year, " +
            "SUM(i.amount) AS total_amount " +
            "FROM Income i " +
            "WHERE i.dateCreated >= :startDate " +
            "AND i.dateCreated <= :endDate " +
            "AND i.account.id = :accountId " +
            "GROUP BY year")
    List<Tuple> aggregateYearly(@Param("accountId") UUID accountId,
                                @Param("startDate") Date startDate,
                                @Param("endDate") Date endDate);
}
