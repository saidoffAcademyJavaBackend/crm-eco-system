package uz.saidoff.crmecosystem.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
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

            Role employee = new Role();
            employee.setName("employee");
            employee.setRoleType(RoleType.EMPLOYEE);
            roleRepository.save(employee);

            Role student = new Role();
            student.setName("student");
            student.setRoleType(RoleType.STUDENT);
            roleRepository.save(student);


            Role intern = new Role();
            intern.setName("intern");
            intern.setRoleType(RoleType.INTERN);
            roleRepository.save(intern);

            userRepository.save(instance.createUser(
                    "admin",
                    "Admin",
                    "123",
                    passwordEncoder.encode("123"),
                    superAdmin,
                    Arrays.stream(Permissions.values()).toList()));

            Role owner = new Role();
            superAdmin.setName("owner");
            superAdmin.setRoleType(RoleType.OWNER);
            roleRepository.save(owner);

            userRepository.save(instance.createUser(
                    "Owner",
                    "Owner",
                    "321",
                    passwordEncoder.encode("321"),
                    owner,
                    Arrays.stream(Permissions.values()).toList()));

        }
    }
}
