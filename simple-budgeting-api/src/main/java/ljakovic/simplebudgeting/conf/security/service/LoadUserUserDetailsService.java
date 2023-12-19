package ljakovic.simplebudgeting.conf.security.service;

import ljakovic.simplebudgeting.appuser.repo.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
public class LoadUserUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepo appUserRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return appUserRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username '" + username + "' not found"));
    }
}
