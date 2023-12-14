package ljakovic.simplebudgeting.auth.service;

import ljakovic.simplebudgeting.auth.dto.AuthResponseDto;
import ljakovic.simplebudgeting.auth.dto.LoginReqDto;
import ljakovic.simplebudgeting.auth.dto.RegisterReqDto;
import ljakovic.simplebudgeting.appuser.model.AppUser;
import ljakovic.simplebudgeting.appuser.repo.AppUserRepo;
import ljakovic.simplebudgeting.conf.security.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {

    @Autowired
    private AppUserRepo repo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private AuthenticationManager authManager;

    public AuthResponseDto register(RegisterReqDto reqDto) {
        AppUser appUser = AppUser.builder()
                .firstName(reqDto.getFirstName())
                .lastName(reqDto.getLastName())
                .username(reqDto.getUsername())
                .password(passwordEncoder.encode(reqDto.getPassword()))
                .dateCreated(new Date())
                .dateModified(new Date())
                .build();

        repo.save(appUser);

        final String jwt = jwtUtil.generateJwt(appUser);

        return AuthResponseDto.builder()
                .accessToken(jwt)
                .build();
    }

    public AuthResponseDto login(LoginReqDto reqDto) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(reqDto.getUsername(), reqDto.getPassword())
        );

        final AppUser appUser = repo.findByUsername(reqDto.getUsername()).orElseThrow();

        final String jwt = jwtUtil.generateJwt(appUser);

        return AuthResponseDto.builder()
                .accessToken(jwt)
                .build();
    }
}
