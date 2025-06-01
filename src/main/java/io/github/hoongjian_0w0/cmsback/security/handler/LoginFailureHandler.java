/*
package io.github.hoongjian_0w0.cmsback.security.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.github.hoongjian_0w0.cmsback.common.result.Result;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import io.github.hoongjian_0w0.cmsback.security.exception.UserAuthenticationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

*/
/**
 * User Authentication Failure Handler
 *//*

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // Set the response content type to JSON and UTF-8 encoding
        response.setContentType("application/json;charset=UTF-8");

        String message = "Login failed!";
        int code = ResultCode.UNAUTHORIZED;

        if (exception instanceof BadCredentialsException) {
            message = "Incorrect username or password.";
        } else if (exception instanceof AccountExpiredException) {
            message = "Account has expired.";
        } else if (exception instanceof CredentialsExpiredException) {
            message = "Password has expired.";
        } else if (exception instanceof DisabledException) {
            message = "Account is disabled.";
        } else if (exception instanceof LockedException) {
            message = "Account is locked.";
        } else if (exception instanceof InternalAuthenticationServiceException) {
            message = "Username is empty.";
        } else if (exception instanceof UserAuthenticationException) {
            message = exception.getMessage(); // Custom token parsing error
        }

        String resultJson = JSON.toJSONString(Result.error()
                .code(code)
                .message(message), SerializerFeature.DisableCircularReferenceDetect);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(resultJson.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();
    }

}
*/
