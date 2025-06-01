package io.github.hoongjian_0w0.cmsback.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * Custom Authentication Handler
 */
public class UserAuthenticationException extends AuthenticationException {

    public UserAuthenticationException(String msg) {
        super(msg);
    }

}
