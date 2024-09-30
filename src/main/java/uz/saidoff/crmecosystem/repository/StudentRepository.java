package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.Permissions;
import uz.saidoff.crmecosystem.enums.RoleType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface StudentRepository extends JpaRepository<User, UUID> {
//    List<User> findByGroupIdAndRoleRoleTypeAndDeletedFalse(UUID groupId, RoleType roleType);

    Optional<User> findByIdAndRoleRoleTypeAndDeletedFalse(UUID userId, RoleType roleType);


}
