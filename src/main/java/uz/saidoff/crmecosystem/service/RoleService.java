package uz.saidoff.crmecosystem.service;

import jdk.jfr.Registered;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.exception.AlreadyExistException;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.RoleMapper;
import uz.saidoff.crmecosystem.payload.RoleDto;
import uz.saidoff.crmecosystem.repository.RoleRepository;
import uz.saidoff.crmecosystem.util.MessageKey;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static org.springframework.data.domain.PageRequest.*;

@Service
@RequiredArgsConstructor
@Transactional
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;


    public Role getRole(UUID roleId) {
        return getRoleById(roleId);
    }


    public List<RoleDto> getAllRoles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Role> roleList = roleRepository.findAllByDeletedFalse(pageable);
        if (roleList.isEmpty()) {
            throw new NotFoundException(MessageKey.ROLE_NOT_FOUND);
        }
        List<RoleDto> dtoList = roleList
                .stream()
                .map(roleMapper::toDto)
                .toList();

        Map<String, Object> response = new HashMap<>();
        response.put("roles", dtoList);
        response.put("totalElements", pageable.getPageSize());
        response.put("totalPages", pageable.getPageNumber());
        return dtoList;
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

    public List<Role> getAllDeletedRoles(int page, int size) {
        Pageable pageable=PageRequest.of(page, size);
        List<Role> roleList = roleRepository.findAllByDeletedTrue(pageable);
        if (roleList.isEmpty()) {
            throw new NotFoundException(MessageKey.ROLE_NOT_FOUND);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("roles", roleList);
        response.put("totalElements", pageable.getPageSize());
        response.put("totalPages", pageable.getPageNumber());

        return roleList;
    }
}
