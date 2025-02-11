package uz.saidoff.crmecosystem.valid;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.ForbiddenException;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

@Component
@Aspect
public class CheckPermissionExecutor {

    @Before(value = "@annotation(checkPermission)")
    public void beforeCheckPermission(CheckPermission checkPermission) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String[] split = checkPermission.value().split(",");
        boolean exist = false;
        outerLoop:
        for (GrantedAuthority authority : principal.getAuthorities()) {
            for (String s : split) {
                if (authority.getAuthority().equals(s)) {
                    exist = true;
                    break outerLoop;
                }
            }
        }
        if (!exist)
            throw new ForbiddenException(checkPermission.value(), MessageService.getMessage(MessageKey.FORBIDDEN));
    }

}
