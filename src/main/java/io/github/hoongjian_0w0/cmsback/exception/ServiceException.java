package io.github.hoongjian_0w0.cmsback.exception;

import lombok.Getter;

/**
 * Custom Service Exception
 */
@Getter
public class ServiceException extends RuntimeException {
    private Integer code;
    /**
     * Service Exception
     * @param code
     * @param message
     */
    public ServiceException(Integer code, String message) {
        super(message);
        this.code = code;
    }
}