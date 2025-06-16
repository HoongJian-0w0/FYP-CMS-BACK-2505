package io.github.hoongjian_0w0.cmsback.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Menu Table
 */
@Getter
@Setter
@ToString
@TableName("cms_menu")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary Key
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Parent Id
     */
    private Long pid;

    /**
     * Parent Name
     */
    @TableField(exist = false)
    private String pName;

    /**
     * Menu Name
     */
    private String title;

    /**
     * Permission String
     */
    private String code;

    /**
     * Route Name
     */
    private String name;

    /**
     * Route Path
     */
    private String path;

    /**
     * Component Path
     */
    private String component;

    /**
     * Menu Type
     */
    private String type;

    /**
     * Menu Icon
     */
    private String icon;

    /**
     * Menu display order
     */
    private Integer orderNum;

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

    @TableField(exist = false)
    private List<Menu> children = new ArrayList<>();

    @TableField(exist = false)
    private Long value;

    @TableField(exist = false)
    private String label;

}
