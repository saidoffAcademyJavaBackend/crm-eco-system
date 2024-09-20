package uz.saidoff.crmecosystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.saidoff.crmecosystem.payload.AuthenticateRequest;
import uz.saidoff.crmecosystem.payload.UserDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.IAuthService;
import uz.saidoff.crmecosystem.util.RestConstant;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("sign-up")
    private ResponseData<UserDto> signUp(@Valid @RequestBody AuthenticateRequest request) {
        return authService.authenticate(request);
    }
}
