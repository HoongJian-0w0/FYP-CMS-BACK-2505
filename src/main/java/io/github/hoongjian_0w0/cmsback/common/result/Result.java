package io.github.hoongjian_0w0.cmsback.common.result;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Result Class
 */
@Data
public class Result {

    private Boolean success;
    private Integer code;
    private String message;
    private Map<String, Object> data = new HashMap<String, Object>();

    // Make the constructor private
    private Result() {}

    // Static method for success response
    public static Result ok() {
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCode.SUCCESS);
        result.setMessage("Success");
        return result;
    }

    // Static method for error response
    public static Result error() {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCode.INTERNAL_SERVER_ERROR);
        result.setMessage("Error");
        return result;
    }

    // The following methods support fluent chaining for convenience
    // Example usage:
    // Result.ok().success(true).message("Custom message").data("item", list)

    public Result success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    public Result message(String message) {
        this.setMessage(message);
        return this;
    }

    public Result code(Integer code) {
        this.setCode(code);
        return this;
    }

    public Result data(String key, Object value) {
        this.data.put(key, value);
        return this;
    }

    public Result data(Map<String, Object> map) {
        this.setData(map);
        return this;
    }

}
