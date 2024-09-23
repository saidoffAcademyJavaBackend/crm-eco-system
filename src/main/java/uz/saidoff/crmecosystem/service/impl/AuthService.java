package uz.saidoff.crmecosystem.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.AlreadyExistException;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.UserMapper;
import uz.saidoff.crmecosystem.payload.AuthenticationRequest;
import uz.saidoff.crmecosystem.payload.AuthenticationResponse;
import uz.saidoff.crmecosystem.payload.RegistrationRequest;
import uz.saidoff.crmecosystem.payload.UserDto;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.security.JWTProvider;
import uz.saidoff.crmecosystem.service.IAuthService;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    @Override
    public ResponseData<UserDto> registration(RegistrationRequest request) {
        existByPhone(request.getPhoneNumber());
        User user = userMapper.toEntity(request);
        user = userRepository.save(user);
        return ResponseData.successResponse(userMapper.toDto(user));
    }
    @Override
    public ResponseData<AuthenticationResponse> authenticate(AuthenticationRequest request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getPhoneNumber(),
                        request.getPassword()));
        User user = userRepository.findByPhoneNumber(request.getPhoneNumber()).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.USERNAME_NOT_FOUND)));
        String token = jwtProvider.generateAccessToken(user);
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(token);
        authenticationResponse.setUser(userMapper.toDto(user));
        return ResponseData.successResponse(authenticationResponse);
    }

    private void existByPhone(String phone) {
        if (userRepository.existsByPhoneNumber(phone)) {
            throw new AlreadyExistException(MessageService.getMessage(MessageKey.PHONE_NUMBER_ALREADY_USED));
        }
    }

}
