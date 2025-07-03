package io.github.hoongjian_0w0.cmsback.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleMenuDTO {
    Long roleId;
    List<Long> menuIds;
}
