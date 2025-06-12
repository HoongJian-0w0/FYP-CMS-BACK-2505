package io.github.hoongjian_0w0.cmsback.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Role–Menu Mapping Table
 */
@Getter
@Setter
@TableName("cms_role_menu")
public class RoleMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Role ID
     */
    private Long roleId;

    /**
     * Menu ID
     */
    private Long menuId;

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
