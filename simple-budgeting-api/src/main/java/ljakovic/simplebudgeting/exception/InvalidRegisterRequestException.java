package ljakovic.simplebudgeting.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Username or password don't match the pattern")
public class InvalidRegisterRequestException extends RuntimeException{
    public InvalidRegisterRequestException(String exceptionMessage) {
        super(exceptionMessage);
    }
}
