package io.github.hoongjian_0w0.cmsback.mapper;

import io.github.hoongjian_0w0.cmsback.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Menu Table Mapper
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {

    List<Menu> getMenuByUserId(Long userId);

    List<Menu> getMenuByRoleId(Long roleId);

}
