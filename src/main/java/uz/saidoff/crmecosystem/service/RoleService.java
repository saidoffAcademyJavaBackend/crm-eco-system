package uz.saidoff.crmecosystem.service;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.enums.RoleType;
import uz.saidoff.crmecosystem.exception.AlreadyExistException;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.RoleMapper;
import uz.saidoff.crmecosystem.payload.RoleDto;
import uz.saidoff.crmecosystem.repository.RoleRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.util.MessageKey;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;


    public ResponseData<?> addRole(RoleDto roleDto) {
        if (roleDto.getRoleType().equals(RoleType.SUPER_ADMIN) || roleDto.getRoleType().equals(RoleType.OWNER)) {
            throw new AlreadyExistException(MessageKey.SUPER_ADMIN_OWNER_ROLES_CANNOT_BE_CREATED);
        }
        Role role = new Role();
        role.setName(roleDto.getName());
        role.setRoleType(roleDto.getRoleType());
        roleRepository.save(role);
        return new ResponseData<>("Role has successfully been added", true);
    }

    public ResponseData<?> updateRole(UUID roleId, RoleDto roleDto) {
        Role role = roleRepository.findById(roleId).orElseThrow(()
                -> new NotFoundException(MessageKey.ROLE_NOT_FOUND));

        role.setName(roleDto.getName());
        if (roleDto.getRoleType().equals(RoleType.SUPER_ADMIN) || roleDto.getRoleType().equals(RoleType.OWNER)) {
            throw new AlreadyExistException(MessageKey.SUPER_ADMIN_OWNER_ROLES_CANNOT_BE_CREATED);
        }
        role.setRoleType(roleDto.getRoleType());
        roleRepository.save(role);
        return new ResponseData<>("Role with id: " + roleId + " has successfully been updated", true);
    }

    public RoleDto getRole(UUID roleId) {
        Role role = roleRepository.findById(roleId).orElseThrow(()
                -> new NotFoundException(MessageKey.ROLE_NOT_FOUND));
        return roleMapper.toDto(role);
    }


    public ResponseData<?> getAllRoles(int page, int size) {
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
        return new ResponseData<>(response, true);
    }

    public void deleteRole(UUID roleId) {
        Role role = getRoleById(roleId);
        role.setDeleted(true);
        roleRepository.save(role);
    }

    public Role getRoleById(UUID roleId) {
        Optional<Role> optionalRole = roleRepository.findByIdAndDeletedFalse(roleId);
        if (optionalRole.isPresent()) {
            return optionalRole.get();
        }
        throw new NotFoundException(MessageKey.ROLE_NOT_FOUND);
    }

    public ResponseData<?> getAllDeletedRoles(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Role> roleList = roleRepository.findAllByDeletedTrue(pageable);
        if (roleList.isEmpty()) {
            throw new NotFoundException(MessageKey.ROLE_NOT_FOUND);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("roles", roleList);
        response.put("totalElements", pageable.getPageSize());
        response.put("totalPages", pageable.getPageNumber());
        return new ResponseData<>(response, true);
    }
}

