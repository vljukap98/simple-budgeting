package ljakovic.simplebudgeting.auth.endpoint;

import ljakovic.simplebudgeting.auth.dto.AuthResponseDto;
import ljakovic.simplebudgeting.auth.dto.LoginReqDto;
import ljakovic.simplebudgeting.auth.dto.RegisterReqDto;
import ljakovic.simplebudgeting.auth.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthEndpoint {

    @Autowired
    private AuthService service;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody RegisterReqDto reqDto) {
        return ResponseEntity.ok(service.register(reqDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginReqDto reqDto) {
        return ResponseEntity.ok(service.login(reqDto));
    }
}
