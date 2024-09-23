package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String phoneNumber) throws UsernameNotFoundException {
        if (phoneNumber.isBlank() || phoneNumber.isEmpty()){
            throw new UsernameNotFoundException(MessageService.getMessage(MessageKey.NULL_USERNAME_FROM_TOKEN));
        }
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.USERNAME_NOT_FOUND)));

    }
}
