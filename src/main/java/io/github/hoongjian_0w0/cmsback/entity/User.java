package io.github.hoongjian_0w0.cmsback.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * User table
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
    @JsonIgnore
    private String password;

    /**
     * Account status (0: active, 1: disabled)
     */
    private String status;

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
     * User type (0: admin, 1: regular)
     */
    private String userType;

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
    private Long createBy;

    /**
     * Creation time
     */
    private LocalDateTime createTime;

    /**
     * Updated by (user ID)
     */
    private Long updateBy;

    /**
     * Update time
     */
    private LocalDateTime updateTime;

    /**
     * Deletion flag (0: not deleted, 1: deleted)
     */
    private Integer deleted;

}
