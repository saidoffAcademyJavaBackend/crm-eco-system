package uz.saidoff.crmecosystem.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.exception.AlreadyExistException;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.RoleDto;
import uz.saidoff.crmecosystem.repository.RoleRepository;
import uz.saidoff.crmecosystem.util.MessageKey;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRole(UUID roleId) {
        return getRoleById(roleId);
    }


    public List<Role> getAllRoles() {
//        PageRequest.of()
        List<Role> roleList = roleRepository.findAllByDeletedFalse();
        if (roleList.isEmpty()) {
            throw new NotFoundException(MessageKey.ROLE_NOT_FOUND);
        }
        return roleList;
    }

    public Role addRole(Role role) {
        Optional<Role> optionalRole = roleRepository.findByIdAndDeletedFalse(role.getId());
        if (optionalRole.isPresent()) {
            throw new AlreadyExistException(MessageKey.ROLE_ALREADY_EXIST);
        }
        Role newRole = new Role();
        newRole.setName(role.getName());
        newRole.setRoleType(role.getRoleType());
        return roleRepository.save(role);
    }

    public void deleteRole(UUID roleId) {
        Role role = getRoleById(roleId);
        role.setDeleted(true);
        roleRepository.save(role);
    }

    public Role updateRole(UUID roleId, Role role) {
        Role roleById = getRoleById(roleId);
        roleById.setName(role.getName());
        roleById.setRoleType(role.getRoleType());
        roleById.setUpdatedAt(Timestamp.from(Instant.now()));
        roleById.setCreatedAt(Timestamp.from(Instant.now()));
        return roleRepository.save(roleById);
    }

    public Role getRoleById(UUID roleId) {
        Optional<Role> byIdAndDeletedFalse = roleRepository.findByIdAndDeletedFalse(roleId);
        if (byIdAndDeletedFalse.isPresent()) {
            return byIdAndDeletedFalse.get();
        }
        throw new NotFoundException(MessageKey.ROLE_NOT_FOUND);
    }
}
