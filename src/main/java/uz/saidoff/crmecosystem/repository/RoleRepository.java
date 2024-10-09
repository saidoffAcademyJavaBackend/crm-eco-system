package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.enums.RoleType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByRoleType(RoleType roleType);

    Optional<Role> findByIdIsAndDeletedFalse(UUID roleId);

    List<Role> findAllByDeletedFalse();

    Optional<Role> findByIdAndDeletedFalse(UUID roleId);

}
