package uz.saidoff.crmecosystem.service;

import uz.saidoff.crmecosystem.payload.AuthenticateRequest;
import uz.saidoff.crmecosystem.payload.UserDto;
import uz.saidoff.crmecosystem.response.ResponseData;

public interface IAuthService {

    ResponseData<UserDto> authenticate(AuthenticateRequest request);


}
