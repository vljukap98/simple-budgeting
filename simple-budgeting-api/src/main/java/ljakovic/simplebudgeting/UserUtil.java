package ljakovic.simplebudgeting;

import org.springframework.security.core.context.SecurityContextHolder;

public class UserUtil {

    public String getLoggedInUserUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

    }
}
