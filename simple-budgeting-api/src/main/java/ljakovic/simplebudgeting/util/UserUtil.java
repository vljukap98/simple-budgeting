package ljakovic.simplebudgeting.util;

import ljakovic.simplebudgeting.appuser.model.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserUtil {

    public UUID getLoggedInUserId() {
        AppUser user = (AppUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return user.getId();
    }
}
