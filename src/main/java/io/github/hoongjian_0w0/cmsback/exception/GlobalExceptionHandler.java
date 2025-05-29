package io.github.hoongjian_0w0.cmsback.exception;

import io.github.hoongjian_0w0.cmsback.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * GlobalExceptionHandler Class
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Capture Service Error (ServiceException.class)
     * @param serviceError
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException serviceError) {
        return Result.error().code(serviceError.getCode()).message(serviceError.getMessage());
    }
}