package uz.saidoff.crmecosystem.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.enums.Permissions;
import uz.saidoff.crmecosystem.enums.RoleType;
import uz.saidoff.crmecosystem.factory.UserFactorySingleton;
import uz.saidoff.crmecosystem.repository.RoleRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (initMode.equals("always")) {
            UserFactorySingleton instance = UserFactorySingleton.getInstance();

            Role superAdmin = new Role();
            superAdmin.setName("super-admin");
            superAdmin.setRoleType(RoleType.SUPER_ADMIN);
            roleRepository.save(superAdmin);

            userRepository.save(instance.createUser("admin", "Admin", "123", passwordEncoder.encode("123"), superAdmin, Arrays.stream(Permissions.values()).toList()));


        }
    }
}
