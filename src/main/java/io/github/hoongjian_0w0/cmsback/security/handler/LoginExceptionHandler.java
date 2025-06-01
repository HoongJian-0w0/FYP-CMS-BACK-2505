package io.github.hoongjian_0w0.cmsback.security.handler;

import io.github.hoongjian_0w0.cmsback.common.result.Result;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class LoginExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public Result handleAuthException(AuthenticationException ex) {
        String message = "Login failed!";

        if (ex instanceof BadCredentialsException) {
            message = "Incorrect username or password.";
        } else if (ex instanceof InternalAuthenticationServiceException) {
            message = "Username is empty.";
        } else if (ex instanceof UsernameNotFoundException) {
            message = "Username not found.";
        } else if (ex instanceof AccountExpiredException) {
            message = "Account has expired.";
        } else if (ex instanceof CredentialsExpiredException) {
            message = "Password has expired.";
        } else if (ex instanceof DisabledException) {
            message = "Account is disabled.";
        } else if (ex instanceof LockedException) {
            message = "Account is locked.";
        }

        return Result.error().code(ResultCode.UNAUTHORIZED).message(message);
    }
}
