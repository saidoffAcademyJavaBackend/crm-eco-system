package uz.saidoff.crmecosystem.entity.auth;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Permission  extends AbsEntity implements GrantedAuthority {

    private String authority;

    private String description;

    @Override
    public String getAuthority() {
        return authority;
    }
}
