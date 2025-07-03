package io.github.hoongjian_0w0.cmsback.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User Table
 */
@Getter
@Setter
@ToString
@TableName("cms_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Username
     */
    private String username;

    /**
     * First name
     */
    private String firstName;

    /**
     * Last name
     */
    private String lastName;

    /**
     * Nickname
     */
    private String nickName;

    /**
     * Password
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    /**
     * Email
     */
    private String email;

    /**
     * Phone number
     */
    private String phone;

    /**
     * Gender (0: male, 1: female, 2: unknown)
     */
    private String gender;

    /**
     * Avatar URL
     */
    private String avatar;

    /**
     * Roles
     */
    @TableField(exist = false)
    private List<Long> roleIds;

    /**
     * Account not expired (1: yes, 0: expired)
     */
    private Boolean accountNonExpired;

    /**
     * Account not locked (1: yes, 0: locked)
     */
    private Boolean accountNonLocked;

    /**
     * Credentials not expired (1: yes, 0: expired)
     */
    private Boolean credentialsNonExpired;

    /**
     * Account enabled (1: yes, 0: disabled)
     */
    private Boolean enabled;

    /**
     * Created by (user ID)
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    /**
     * Creation time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * Updated by (user ID)
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    /**
     * Update time
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * Delete Flag (0 = exists, 1 = deleted)
     */
    private Boolean delFlag;

}
