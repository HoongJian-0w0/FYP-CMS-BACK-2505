package io.github.hoongjian_0w0.cmsback.service;

import io.github.hoongjian_0w0.cmsback.dto.AssignTreeDTO;
import io.github.hoongjian_0w0.cmsback.dto.PasswordUpdateDTO;
import io.github.hoongjian_0w0.cmsback.dto.ProfileUpdateDTO;
import io.github.hoongjian_0w0.cmsback.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import io.github.hoongjian_0w0.cmsback.vo.AssignTreeVo;
import io.github.hoongjian_0w0.cmsback.vo.UserInfoVo;

/**
 * User table Service Class
 */
public interface IUserService extends IService<User> {

    UserInfoVo getCurrentUserInfo();

    AssignTreeVo getAssignTree(AssignTreeDTO assignTreeDTO);

    UserInfoVo updateProfile(ProfileUpdateDTO profileUpdateDTO);

    boolean updatePassword(PasswordUpdateDTO passwordDTO);
}
