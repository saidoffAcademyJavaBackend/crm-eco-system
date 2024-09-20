package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.UserMapper;
import uz.saidoff.crmecosystem.payload.UserCreateDto;
import uz.saidoff.crmecosystem.payload.UserDto;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserCrudService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public ResponseData<UserDto> create(UserCreateDto userDto) {
        User user=userMapper.toEntity(userDto);
        userRepository.save(user);
        return new ResponseData<>(userMapper.toDto(user),true);
    }

    public ResponseData<UserDto> update(UUID userId, UserDto userDto) {
        Optional<User> userOptional =userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        User user=userOptional.get();
        user=userMapper.update(userDto,user);
        userRepository.save(user);
        return new ResponseData<>(userMapper.toDto(user),true);
    }

    public ResponseData<UserDto> getUser(UUID userId) {
        Optional<User> userOptional =userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("User not found");
        }
        return new ResponseData<>(userMapper.toDto(userOptional.get()),true);
    }

    public ResponseData<?> getAllUser(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> users = userRepository.findAll(pageable);
        if (users.isEmpty()){
            throw new  NotFoundException("Users not found!");
        }


        Map<String, Object> response = new HashMap<>();
        response.put("data", userMapper.toDto(users.toList()));
        response.put("total", users.getTotalElements());
        response.put("totalPages", users.getTotalPages());

        return new ResponseData<>(response,true);
    }
}
