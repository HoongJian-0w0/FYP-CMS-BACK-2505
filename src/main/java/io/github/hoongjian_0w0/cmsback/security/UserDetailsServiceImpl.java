package io.github.hoongjian_0w0.cmsback.security;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.hoongjian_0w0.cmsback.entity.User;
import io.github.hoongjian_0w0.cmsback.mapper.MenuMapper;
import io.github.hoongjian_0w0.cmsback.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.equals("")) {
            throw new InternalAuthenticationServiceException("");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username) .eq("del_flag", 0);;
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            throw new UsernameNotFoundException("");
        }

        List<String> permissionList = null;

        return new LoginUserDetails(user, permissionList);
    }

}
