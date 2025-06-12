package io.github.hoongjian_0w0.cmsback.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;
    private String verifyCode;
    private String captchaUUID;
}
