package uz.saidoff.crmecosystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.enums.RoleType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByRoleType(RoleType roleType);

    Optional<Role> findByIdIsAndDeletedFalse(UUID roleId);

    List<Role> findAllByDeletedFalse(Pageable pageable);

    List<Role> findAllByDeletedTrue(Pageable pageable);

    Optional<Role> findByIdAndDeletedFalse(UUID roleId);


    List<Role> findAllByIdIn(List<UUID> roleIds);

}
