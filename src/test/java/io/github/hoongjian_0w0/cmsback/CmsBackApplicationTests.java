package io.github.hoongjian_0w0.cmsback;

import io.github.hoongjian_0w0.cmsback.security.jwt.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CmsBackApplicationTests {

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

}
