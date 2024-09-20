package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.saidoff.crmecosystem.entity.auth.Permission;

import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
}
