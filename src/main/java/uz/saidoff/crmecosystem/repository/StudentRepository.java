package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.RoleType;

import java.util.List;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<User, UUID> {
    List<User> findByGroupIdAndRoleRoleTypeAndDeletedFalse(UUID groupId, RoleType roleType);
    User findByIdAndRole(UUID userId, Role role);
}
