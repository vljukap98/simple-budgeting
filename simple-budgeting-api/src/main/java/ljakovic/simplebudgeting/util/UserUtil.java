package ljakovic.simplebudgeting.util;

import ljakovic.simplebudgeting.appuser.model.AppUser;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    public String getLoggedInUserId() {
        AppUser user = (AppUser) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        return user.getId().toString();
    }
}
