package io.github.hoongjian_0w0.cmsback.security;

import com.alibaba.fastjson.annotation.JSONField;
import io.github.hoongjian_0w0.cmsback.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LoginUserDetails implements UserDetails {

    private User user;

    private List<String> authoritiesStrings;

    @JSONField(serialize = false)
    private List<SimpleGrantedAuthority> authorities;

    public LoginUserDetails(User user, List<String> authoritiesStrings) {
        this.user = user;
        this.authoritiesStrings = authoritiesStrings;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authorities != null) {
            return authorities;
        }

        authorities = new ArrayList<>();
        if (authoritiesStrings != null) {
            for (String authority : authoritiesStrings) {
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }
        return authorities;
    }
}
