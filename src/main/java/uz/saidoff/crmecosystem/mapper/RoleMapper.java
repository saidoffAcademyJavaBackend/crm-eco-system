package uz.saidoff.crmecosystem.mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.payload.RoleDto;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private RoleDto toDto(Role role){
        RoleDto dto = new RoleDto();
        dto.setName(role.getName());
        dto.setRoleType(role.getRoleType());
        return dto;
    }

    private Role toEntity(RoleDto dto){
        Role role = new Role();
        role.setName(dto.getName());
        role.setRoleType(dto.getRoleType());
        return role;
    }
}
