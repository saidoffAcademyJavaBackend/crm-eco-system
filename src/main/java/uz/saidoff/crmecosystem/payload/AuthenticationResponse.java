package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.auth.User;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    private String token;
    private User user;

}
