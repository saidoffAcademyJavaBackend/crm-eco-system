package uz.saidoff.crmecosystem.service;

import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.CrossParameterConstraintValidatorContextImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.factory.UserFactorySingleton;
import uz.saidoff.crmecosystem.mapper.UserMapper;
import uz.saidoff.crmecosystem.payload.PasswordUpdateDto;
import uz.saidoff.crmecosystem.payload.UserCreateDto;
import uz.saidoff.crmecosystem.payload.UserDto;
import uz.saidoff.crmecosystem.repository.RoleRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.valid.PasswordValidate;
import uz.saidoff.crmecosystem.valid.PasswordValidation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    UserFactorySingleton instance = UserFactorySingleton.getInstance();

    public ResponseData<?> create(UserCreateDto userDto) {
        User user = userMapper.toEntity(userDto);
        UUID roleId = userDto.getRoleId();
        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if (optionalRole.isEmpty()) {
            throw new IllegalArgumentException("Role not found");
        }
        instance.createUser(userDto, optionalRole.get());

        userRepository.save(user);
        return ResponseData.successResponse("success");
    }

    public ResponseData<UserDto> update(UUID userId, UserDto userDto) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        User user = userOptional.get();
        user = userMapper.update(userDto, user);
        userRepository.save(user);
        return new ResponseData<>(userMapper.toDto(user), true);
    }

    public ResponseData<UserDto> getUser(UUID userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return new ResponseData<>(userMapper.toDto(userOptional.get()), true);
    }

    public ResponseData<?> getAllUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        if (users.isEmpty()) {
            throw new NotFoundException("Users not found!");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", userMapper.toDto(users.toList()));
        response.put("total", users.getTotalElements());
        response.put("totalPages", users.getTotalPages());

        return new ResponseData<>(response, true);
    }

    public ResponseData<?> updatePassword(UUID userId, PasswordUpdateDto passwordUpdateDto) {

        if (userId == null) {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userId = user.getId();
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        User user = optionalUser.get();
        user.setPassword(passwordEncoder.encode(passwordUpdateDto.getPassword()));
        userRepository.save(user);
        return ResponseData.successResponse("success");
    }
}
