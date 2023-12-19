package ljakovic.simplebudgeting.appuser.service;

import jakarta.persistence.EntityNotFoundException;
import ljakovic.simplebudgeting.appuser.dto.AppUserDto;
import ljakovic.simplebudgeting.appuser.mapper.AppUserMapper;
import ljakovic.simplebudgeting.appuser.model.AppUser;
import ljakovic.simplebudgeting.appuser.repo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepo repo;

    @Autowired
    private AppUserMapper mapper;

    public AppUser getAppUserById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public AppUserDto getById(Integer id) {
        AppUser appUser = repo.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        return mapper.mapTo(appUser);
    }
}
