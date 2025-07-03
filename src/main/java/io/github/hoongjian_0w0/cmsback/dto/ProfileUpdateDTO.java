package io.github.hoongjian_0w0.cmsback.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileUpdateDTO {
    private String firstName;
    private String lastName;
    private String nickName;
    private String email;
    private String phone;
    private String gender;
    private String avatar;
}
