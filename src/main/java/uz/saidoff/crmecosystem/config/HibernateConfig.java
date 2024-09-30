package uz.saidoff.crmecosystem.config;

import jakarta.annotation.Nonnull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import uz.saidoff.crmecosystem.util.UserSession;

import java.util.Optional;
import java.util.UUID;

@Configuration
public class HibernateConfig implements AuditorAware<UUID> {

    private final UserSession userSession;

    public HibernateConfig(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    @Nonnull
    public Optional<UUID> getCurrentAuditor() {
        return Optional.ofNullable(userSession.getUser() != null ? userSession.getUser().getId() : null);
    }
}
