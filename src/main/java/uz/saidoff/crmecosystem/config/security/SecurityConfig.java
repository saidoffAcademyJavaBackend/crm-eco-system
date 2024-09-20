package uz.saidoff.crmecosystem.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import uz.saidoff.crmecosystem.util.RestConstant;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       http
               .csrf(AbstractHttpConfigurer::disable)
               .authorizeHttpRequests(
                       auth -> auth
                               .requestMatchers(
                                       "api/v1/auth/**",
                                       "/v2/api-docs",
                                       "/v3/api-docs",
                                       "/v3/api-docs/**",
                                       "/swagger-resources",
                                       "/swagger-resources/**",
                                       "/configuration/ui",
                                       "/configuration/security",
                                       "/swagger-ui/**",
                                       "/webjars/**",
                                       "/swagger-ui.html/**").permitAll()
                               .anyRequest().authenticated()
               );
       return http.build();
    }
}
