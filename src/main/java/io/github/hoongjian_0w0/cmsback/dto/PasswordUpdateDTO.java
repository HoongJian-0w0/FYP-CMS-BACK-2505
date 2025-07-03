package io.github.hoongjian_0w0.cmsback.dto;

import lombok.Data;

@Data
public class PasswordUpdateDTO {
    String oldPassword;
    String newPassword;
    String confirmPassword;
}
