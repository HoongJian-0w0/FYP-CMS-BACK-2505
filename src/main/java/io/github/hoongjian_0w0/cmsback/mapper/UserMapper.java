package io.github.hoongjian_0w0.cmsback.mapper;

import io.github.hoongjian_0w0.cmsback.dto.ProfileUpdateDTO;
import io.github.hoongjian_0w0.cmsback.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

/**
 * User table Mapper
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

    int updatePassword(@Param("userId") Long userId, @Param("newPassword") String newPassword);

}
