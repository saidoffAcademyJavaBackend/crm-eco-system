package uz.saidoff.crmecosystem.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.saidoff.crmecosystem.payload.*;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.IAuthService;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final IAuthService authService;

    @PostMapping("sign-up")
    private ResponseData<UserDto> signUp(@Valid @RequestBody RegistrationRequest request) {
        return authService.registration(request);
    }
    @PostMapping("sign-in")
    private ResponseData<AuthenticationResponse> signIn(@Valid @RequestBody AuthenticationRequest request) {
        return authService.authenticate(request);
    }
}
