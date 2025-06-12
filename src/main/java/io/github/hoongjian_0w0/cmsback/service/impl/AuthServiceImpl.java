package io.github.hoongjian_0w0.cmsback.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.code.kaptcha.Producer;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import io.github.hoongjian_0w0.cmsback.dto.LoginDTO;
import io.github.hoongjian_0w0.cmsback.exception.ServiceException;
import io.github.hoongjian_0w0.cmsback.security.LoginUserDetails;
import io.github.hoongjian_0w0.cmsback.security.jwt.JwtUtil;
import io.github.hoongjian_0w0.cmsback.security.util.TokenExtractor;
import io.github.hoongjian_0w0.cmsback.service.IAuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public Producer producer;

    private final int CAPTCHA_EXPIRATION = 60;

    @Override
    public Map<String, Object> genCaptcha() {
        String verifyCode = producer.createText();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String captchaKey = "kaptchaId:" + uuid;

        BufferedImage image = producer.createImage(verifyCode);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write captcha image", e);
        }
        String base64Image = Base64.getEncoder().encodeToString(outputStream.toByteArray());

        stringRedisTemplate.opsForValue().set(captchaKey, verifyCode, CAPTCHA_EXPIRATION, TimeUnit.SECONDS);

        Map<String, Object> result = new HashMap<>();
        result.put("uuid", uuid);
        result.put("img", "data:image/png;base64," + base64Image);
        result.put("expiresIn", CAPTCHA_EXPIRATION);

        return result;
    }

    @Override
    public Map<String,Object> login(LoginDTO loginDTO) {
        // Verify Captcha
        verifyCaptchaOrThrow(loginDTO.getVerifyCode(), loginDTO.getCaptchaUUID());

        // 1. Wrap the Authentication object with username and password
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());
        // 2. Perform authentication
        Authentication authenticate = authenticationManager.authenticate(authentication);
        // 3. Extract user info from authentication
        LoginUserDetails loginUserDetails = (LoginUserDetails) authenticate.getPrincipal();
        // 4. Convert loginUser to JSON string (e.g. using FastJSON)
        String loginUserString = JSON.toJSONString(loginUserDetails);
        // 5. Generate JWT using custom utility class
        String jwt = JwtUtil.createJWT(loginUserString, null);

        String tokenKey = "token_" + jwt;
        stringRedisTemplate.opsForValue().set(tokenKey, jwt, JwtUtil.JWT_TTL/1000, TimeUnit.SECONDS);

        Map<String,Object> user = new HashMap<>();
        user.put("token", jwt);
        user.put("username", loginUserDetails.getUsername());

        return user;
    }

    @Override
    public Boolean logout(HttpServletRequest request, HttpServletResponse response) {
        String token = TokenExtractor.extractToken(request);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
            System.out.println("JWT: " + "token_"+token);
            stringRedisTemplate.delete("token_"+token);
            return true;
        }
        return false;
    }

    private void verifyCaptchaOrThrow(String verifyCode, String captchaUuid) {
        if (captchaUuid == null || verifyCode == null) {
            throw new ServiceException(ResultCode.BAD_REQUEST, "CAPTCHA UUID and code must not be null.");
        }

        String captchaKey = "kaptchaId:" + captchaUuid;
        String correctCode = stringRedisTemplate.opsForValue().get(captchaKey);

        if (correctCode == null) {
            throw new ServiceException(ResultCode.BAD_REQUEST, "Invalid or expired CAPTCHA.");
        }

        if (!verifyCode.equalsIgnoreCase(correctCode)) {
            throw new ServiceException(ResultCode.BAD_REQUEST, "Incorrect CAPTCHA code.");
        }

        stringRedisTemplate.delete(captchaKey);
    }
}
