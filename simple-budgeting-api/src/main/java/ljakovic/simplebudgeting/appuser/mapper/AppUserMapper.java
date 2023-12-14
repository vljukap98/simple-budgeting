package ljakovic.simplebudgeting.appuser.mapper;

import ljakovic.simplebudgeting.appuser.dto.AppUserDto;
import ljakovic.simplebudgeting.appuser.model.AppUser;
import org.springframework.stereotype.Component;

@Component
public class AppUserMapper {
    public AppUserDto mapTo(AppUser appUser) {
        return AppUserDto.builder()
                .id(appUser.getId().toString())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .username(appUser.getUsername())
                .dateCreated(appUser.getDateCreated().toString())
                .dateModified(appUser.getDateModified().toString())
                .build();
    }
}
