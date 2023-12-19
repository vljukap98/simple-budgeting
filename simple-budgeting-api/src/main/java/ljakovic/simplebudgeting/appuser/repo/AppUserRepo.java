package ljakovic.simplebudgeting.appuser.repo;

import ljakovic.simplebudgeting.appuser.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Integer> {

    Optional<AppUser> findById(Integer id);
    Optional<AppUser> findByUsername(String username);
}
