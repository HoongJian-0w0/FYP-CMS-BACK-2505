package io.github.hoongjian_0w0.cmsback.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Menu Table
 */
@Getter
@Setter
@TableName("cms_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary Key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Menu Name
     */
    private String menuName;

    /**
     * Route Path
     */
    private String path;

    /**
     * Component Path
     */
    private String component;

    /**
     * Visibility (0 = visible, 1 = hidden)
     */
    private String visible;

    /**
     * Status (0 = active, 1 = disabled)
     */
    private String status;

    /**
     * Permission Identifier
     */
    private String perms;

    /**
     * Menu Icon
     */
    private String icon;

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
     * Delete Flag (0 = active, 1 = deleted)
     */
    private Integer delFlag;

    /**
     * Remarks
     */
    private String remark;


}
