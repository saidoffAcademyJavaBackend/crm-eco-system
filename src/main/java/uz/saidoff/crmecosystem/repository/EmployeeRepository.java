package uz.saidoff.crmecosystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.saidoff.crmecosystem.entity.auth.User;

import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<User, UUID> {
    Optional<User> findByIdAndDeletedFalse(UUID id);
    Boolean existsByPhoneNumber(String phoneNumber);
    Page<User> findAllByDeletedFalse(Pageable pageable);

}
