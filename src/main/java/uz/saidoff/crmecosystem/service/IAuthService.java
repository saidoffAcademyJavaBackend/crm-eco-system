package uz.saidoff.crmecosystem.service;

import uz.saidoff.crmecosystem.payload.AuthenticationRequest;
import uz.saidoff.crmecosystem.payload.RegistrationRequest;
import uz.saidoff.crmecosystem.payload.AuthenticationResponse;
import uz.saidoff.crmecosystem.payload.UserDto;
import uz.saidoff.crmecosystem.response.ResponseData;

public interface IAuthService {

    ResponseData<UserDto> registration(RegistrationRequest request);

    ResponseData<AuthenticationResponse> authenticate(AuthenticationRequest request);

}
