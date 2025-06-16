package io.github.hoongjian_0w0.cmsback.service;

import io.github.hoongjian_0w0.cmsback.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * User table Service Class
 */
public interface IUserService extends IService<User> {

    User getCurrentUser();

}
