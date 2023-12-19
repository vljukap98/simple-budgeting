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


    @Query(value = "SELECT " +
            "EXTRACT(MONTH FROM i.date_created) AS month," +
            "EXTRACT(YEAR FROM i.date_created) AS year," +
            "SUM(i.amount) AS total_amount " +
            "FROM income i " +
            "INNER JOIN budget_account ba on ba.id = i.account_id " +
            "WHERE i.date_created >= :startDate " +
            "AND i.date_created <= :endDate " +
            "AND ba.id = :accountId " +
            "GROUP BY month, year", nativeQuery = true)
    List<Tuple> aggregate(@Param("accountId") Integer accountId,
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
    List<Tuple> aggregateYearly(@Param("accountId") Integer accountId,
                                @Param("startDate") Date startDate,
                                @Param("endDate") Date endDate);
}
