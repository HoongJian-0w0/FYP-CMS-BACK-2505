package io.github.hoongjian_0w0.cmsback.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Role Table
 */
@Getter
@Setter
@TableName("cms_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary Key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Role Name
     */
    private String name;

    /**
     * Role Type
     */
    private String label;

    /**
     * Role Type
     */
    private String type;

    /**
     * Role Status (0 = active, 1 = disabled)
     */
    private String status;

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

    /**
     * Remarks
     */
    private String remark;

}
