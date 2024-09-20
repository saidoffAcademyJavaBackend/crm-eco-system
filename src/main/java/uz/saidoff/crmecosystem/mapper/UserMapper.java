package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.RoleType;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.AuthenticateRequest;
import uz.saidoff.crmecosystem.payload.UserCreateDto;
import uz.saidoff.crmecosystem.payload.UserDto;
import uz.saidoff.crmecosystem.repository.RoleRepository;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;


    public  User toEntity(AuthenticateRequest request){
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setRole(roleRepository.findByRoleType(RoleType.EMPLOYEE).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.ROLE_NOT_FOUND))));
        return user;
    }

    public User toEntity(UserCreateDto userCreateDto){
        User user = new User();
        user.setFirstName(userCreateDto.getFirstName());
        user.setLastName(userCreateDto.getLastName());
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));
        user.setPhoneNumber(userCreateDto.getPhoneNumber());
        user.setRole(roleRepository.findByRoleType(userCreateDto.getRole().getRoleType()).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.ROLE_NOT_FOUND))));
        return user;
    }

    public UserDto toDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setRole(user.getRole());
        return userDto;
    }

    public List<UserDto> toDto(List<User> users){
        List<UserDto> userDto = new ArrayList<>();
        for(User user : users){
            userDto.add(toDto(user));
        }
        return userDto;
    }

    public User update(UserDto userDto, User user) {

        if (userDto.getFirstName() != null) {
            user.setFirstName(userDto.getFirstName());
        }
        if (userDto.getLastName() != null) {
            user.setLastName(userDto.getLastName());
        }
        if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getPhoneNumber() != null) {
            user.setPhoneNumber(userDto.getPhoneNumber());
        }
        if (userDto.getRole() != null) {
            user.setRole(roleRepository.findByRoleType(userDto.getRole().getRoleType())
                    .orElseThrow(() -> new NotFoundException(MessageService.getMessage(MessageKey.ROLE_NOT_FOUND))));
        }
        return user;
    }
}
