package uz.saidoff.crmecosystem.mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.payload.RoleDto;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    public RoleDto toDto(Role role){
        RoleDto dto = new RoleDto();
        dto.setRoleId(role.getId());
        dto.setName(role.getName());
        dto.setRoleType(role.getRoleType());
        return dto;
    }


    public Role toEntity(RoleDto dto){
        Role role = new Role();
        role.setName(dto.getName());
        role.setRoleType(dto.getRoleType());
        return role;
    }
}
