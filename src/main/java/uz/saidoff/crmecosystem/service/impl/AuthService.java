package uz.saidoff.crmecosystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.AlreadyExistException;
import uz.saidoff.crmecosystem.mapper.UserMapper;
import uz.saidoff.crmecosystem.payload.AuthenticateRequest;
import uz.saidoff.crmecosystem.payload.UserDto;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.IAuthService;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public ResponseData<UserDto> authenticate(AuthenticateRequest request) {
        existByPhone(request.getPhoneNumber());
        existByUsername(request.getUsername());
        User user = userMapper.toEntity(request);
        user = userRepository.save(user);
        return ResponseData.successResponse(userMapper.toDto(user));
    }

    private void existByPhone(String phone) {
        if (userRepository.existsByPhoneNumber(phone)) {
            throw new AlreadyExistException(MessageService.getMessage(MessageKey.PHONE_NUMBER_ALREADY_USED));
        }
    }
    private void existByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new AlreadyExistException(MessageService.getMessage(MessageKey.USER_ALREADY_REGISTERED));
        }
    }
}
