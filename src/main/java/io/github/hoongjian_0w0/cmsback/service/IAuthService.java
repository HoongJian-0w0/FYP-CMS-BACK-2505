package io.github.hoongjian_0w0.cmsback.service;

import io.github.hoongjian_0w0.cmsback.dto.LoginDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;

public interface IAuthService {

    Map<String,Object> genCaptcha();

    Map<String,Object> login(LoginDTO loginDTO);

    Boolean logout(HttpServletRequest request, HttpServletResponse response);

}
