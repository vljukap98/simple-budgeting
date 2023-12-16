package ljakovic.simplebudgeting.util;

import ljakovic.simplebudgeting.appuser.model.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    public String getLoggedInUserUsername() {
        AppUser user = (AppUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return user.getUsername();
    }
}
