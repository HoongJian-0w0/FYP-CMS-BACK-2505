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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Anonymous Access Handler
 */
@Component
public class AnonymousAuthenticationHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        response.setContentType("application/json;charset=UTF-8");

        String message = "Unauthorized access - authentication required.";

        if (authException instanceof UserAuthenticationException) {
            message = authException.getMessage(); // Custom token parsing error
        }

        String resultJson = JSON.toJSONString(
                Result.error().code(ResultCode.UNAUTHORIZED).message(message),
                SerializerFeature.DisableCircularReferenceDetect
        );

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(resultJson.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();
        outputStream.close();

    }

}
