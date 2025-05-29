package io.github.hoongjian_0w0.cmsback.service.impl;

import io.github.hoongjian_0w0.cmsback.entity.User;
import io.github.hoongjian_0w0.cmsback.mapper.UserMapper;
import io.github.hoongjian_0w0.cmsback.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * User table Service Implementation Class
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {}
