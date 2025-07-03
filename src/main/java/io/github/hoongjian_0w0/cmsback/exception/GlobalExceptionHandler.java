package io.github.hoongjian_0w0.cmsback.exception;

import io.github.hoongjian_0w0.cmsback.common.result.Result;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Custom service-layer exception
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException ex) {
        return Result.error().code(ex.getCode()).message(ex.getMessage());
    }

    /**
     * Validation: Missing required request body field
     */
    @ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
    @ResponseBody
    public Result handleValidation(Exception ex) {
        ex.printStackTrace();
        return Result.error().code(ResultCode.BAD_REQUEST).message("Validation error: missing or invalid fields.");
    }

    /**
     * Malformed JSON or missing field in @RequestBody
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public Result handleJsonParseError(HttpMessageNotReadableException ex) {
        ex.printStackTrace();
        return Result.error().code(ResultCode.BAD_REQUEST).message("Invalid request format or missing fields.");
    }

    /**
     * DB constraint violation: NOT NULL, UNIQUE, FK
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public Result handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        ex.printStackTrace();
        return Result.error().code(ResultCode.INTERNAL_SERVER_ERROR).message("Database constraint violated (e.g., missing NOT NULL field).");
    }

    /**
     * Fallback for unknown exceptions
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleAll(Exception ex) {
        ex.printStackTrace();
        return Result.error().code(ResultCode.INTERNAL_SERVER_ERROR).message("Unexpected server error: " + ex.getMessage());
    }

}
