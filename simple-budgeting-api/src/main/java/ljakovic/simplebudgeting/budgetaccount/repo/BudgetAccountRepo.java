package ljakovic.simplebudgeting.budgetaccount.repo;

import ljakovic.simplebudgeting.budgetaccount.model.BudgetAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetAccountRepo extends JpaRepository<BudgetAccount, Integer> {

    Optional<BudgetAccount> findById(Integer id);

    @Query("SELECT ba FROM BudgetAccount ba JOIN ba.user u WHERE u.id = ?1")
    List<BudgetAccount> findByUserId(Integer id);

    @Query("SELECT ba FROM BudgetAccount ba JOIN ba.user u WHERE ba.id = ?1 AND u.id = ?2")
    BudgetAccount findByIdUserId(Integer id, Integer userId);
}
