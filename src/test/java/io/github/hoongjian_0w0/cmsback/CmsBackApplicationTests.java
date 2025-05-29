package io.github.hoongjian_0w0.cmsback;

import io.github.hoongjian_0w0.cmsback.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class CmsBackApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {

        // Generate JWT
        String result = JwtUtil.createJWT("10086", null);
        System.out.println(result);

        try {
            Claims claims = JwtUtil.parseJWT(result);
            String subject = claims.getSubject();
            System.out.println(subject);

        } catch (Exception e) {}

    }

    @Test
    public void generateAdminPassword() {
        String rawPassword = "admin";
        String encodedPassword = passwordEncoder.encode(rawPassword);

        System.out.println("Encoded admin password: " + encodedPassword);
    }

}
