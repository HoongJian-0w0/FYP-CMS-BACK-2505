package io.github.hoongjian_0w0.cmsback.security.util;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class CaptchaConfig {

    @Bean
    public Producer producer() {
        final Properties properties = new Properties();
        // Image Width
        properties.setProperty("kaptcha.image.width","150");
        // Image Height
        properties.setProperty("kaptcha.image.height","50");
        // String Selection
        properties.setProperty("kaptcha.textproducer.char.string","0123456789");
        // Length Of Strings
        properties.setProperty("kaptcha.textproducer.char.length","4");

        final DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(new Config(properties));
        return defaultKaptcha;
    };

}