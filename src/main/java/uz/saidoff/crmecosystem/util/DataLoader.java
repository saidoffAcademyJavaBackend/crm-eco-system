package uz.saidoff.crmecosystem.util;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Balance;
import uz.saidoff.crmecosystem.entity.Category;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.enums.Currency;
import uz.saidoff.crmecosystem.enums.Permissions;
import uz.saidoff.crmecosystem.enums.RoleType;
import uz.saidoff.crmecosystem.factory.UserFactorySingleton;
import uz.saidoff.crmecosystem.repository.BalanceRepository;
import uz.saidoff.crmecosystem.repository.CategoryRepository;
import uz.saidoff.crmecosystem.repository.RoleRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final BalanceRepository balanceRepository;
    private final CategoryRepository categoryRepository;

    @Value("${spring.sql.init.mode}")
    private String initMode;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
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

            Balance balance = new Balance();
            balance.setCurrency(Currency.SUM);
            balance.setAmount(0.0);
            balanceRepository.save(balance);

            Balance balance2 = new Balance();
            balance2.setCurrency(Currency.USD);
            balance2.setAmount(0.0);
            balanceRepository.save(balance2);


            Category category = new Category();
            category.setDescription("Payment for month");
            category.setName("STUDENT_PAYMENT");
            category.setIncome(true);
            categoryRepository.save(category);
        }
    }
}
