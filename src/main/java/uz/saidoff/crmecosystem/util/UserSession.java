package uz.saidoff.crmecosystem.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.mapper.UserMapper;
import uz.saidoff.crmecosystem.payload.UserDto;

@Component
public class UserSession {

    private final UserMapper userMapper;

    public UserSession(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    public UserDto getUser(){
        UserDto userDto = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            userDto = userMapper.toDto((User) authentication.getPrincipal());
        }
        return userDto;
    }
}
