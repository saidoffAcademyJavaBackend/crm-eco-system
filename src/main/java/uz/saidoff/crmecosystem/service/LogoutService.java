package uz.saidoff.crmecosystem.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.security.JWTProvider;
import uz.saidoff.crmecosystem.util.RestConstant;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler{

    private final JWTProvider jwtProvider;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) {

        final String authHeader = request.getHeader(RestConstant.AUTHORIZATION_HEADER);
        final String jwt;
        if (authHeader == null || !authHeader.startsWith(RestConstant.TOKEN_TYPE)){
            return;
        }
        jwt = authHeader.substring(7);
        if (!jwt.isEmpty()){
            jwtProvider.invalidateToken(jwt);
            SecurityContextHolder.clearContext();
        }
    }
}
