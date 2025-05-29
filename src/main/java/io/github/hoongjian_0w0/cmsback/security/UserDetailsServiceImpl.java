package io.github.hoongjian_0w0.cmsback.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.hoongjian_0w0.cmsback.common.result.ResultCode;
import io.github.hoongjian_0w0.cmsback.entity.User;
import io.github.hoongjian_0w0.cmsback.exception.ServiceException;
import io.github.hoongjian_0w0.cmsback.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) {

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        User user = userMapper.selectOne(queryWrapper);

        if(Objects.isNull(user)) {
            throw new ServiceException(ResultCode.UNAUTHORIZED, "User not found: " + username);
        }

        List<String> list = new ArrayList<>();
        list.add("cms-user-fetch");

        return new LoginUserDetails(user, list);
    }

}
