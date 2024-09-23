package uz.saidoff.crmecosystem.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.auth.Permission;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.RoleType;
import uz.saidoff.crmecosystem.repository.PermissionRepository;
import uz.saidoff.crmecosystem.repository.RoleRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;

import java.util.Collections;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {
            Permission readOnly = new Permission();
            readOnly.setAuthority("READ_ONLY");
            readOnly.setDescription("This permission accesses for only read");
            readOnly = permissionRepository.save(readOnly);

            Role employeeRole = new Role();
            employeeRole.setName("employee");
            employeeRole.setRoleType(RoleType.EMPLOYEE);
            employeeRole.setPermission(Collections.singleton(readOnly));
            roleRepository.save(employeeRole);


        }
    }
}
