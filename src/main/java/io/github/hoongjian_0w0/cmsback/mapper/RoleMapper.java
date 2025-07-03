package io.github.hoongjian_0w0.cmsback.mapper;

import io.github.hoongjian_0w0.cmsback.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Role Table Mapper
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    List<String> getRoleCodesByUserId(Long userId);

}
