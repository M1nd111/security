package plugin.acc.auth.exceptions;

import org.springframework.http.*;

public class AuthenticationException extends AuthAppException {

    public AuthenticationException() {
        super("Неправильный логин или пароль", HttpStatus.UNAUTHORIZED);
    }

    protected AuthenticationException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    protected AuthenticationException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, httpStatus, cause);
    }

}
