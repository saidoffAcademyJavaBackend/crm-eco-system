package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.saidoff.crmecosystem.entity.auth.User;

import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<User, UUID> {

    Boolean existsByPhoneNumber(String phoneNumber);

}
