package uz.saidoff.crmecosystem.controller;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.payload.RoleDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.RoleService;
import uz.saidoff.crmecosystem.valid.CheckPermission;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }


    @CheckPermission("GET_ROLE")
    @GetMapping("/getRole/{roleId}")
    public ResponseData<Role> getRole(@PathVariable("roleId") UUID roleId) {
        Role role = roleService.getRole(roleId);
        return new ResponseData<>(role, true);
    }

    @CheckPermission("GET_ALL_ROLES")
    @GetMapping("/getAllRoles")
    public ResponseData<?> getAllRolesPg(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "10" ) int size) {
        List<RoleDto> allRoles = roleService.getAllRoles(page, size);
        return new ResponseData<>(allRoles, true);
    }

    @CheckPermission("GET_ALL_DELETED_ROLES")
    @GetMapping("/getAllDeletedRoles")
    public ResponseData<?> getAllDeletedRolesPg(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "10" ) int size) {
        List<Role> allRoles = roleService.getAllDeletedRoles(page, size);
        return new ResponseData<>(allRoles, true);
    }

    @CheckPermission("ADD_ROLE")
    @PostMapping("/addRole")
    public ResponseData<?> addRole(@RequestBody Role role) {
        Role addedRole = roleService.addRole(role);
        return new ResponseData<>(addedRole, true);
    }

    @CheckPermission("DELETE_ROLE")
    @DeleteMapping("/deleteRole/{roleId}")
    public ResponseData<?> removeRole(@PathVariable("roleId") UUID roleId) {
        roleService.deleteRole(roleId);
        return new ResponseData<>("Role has been successfully deleted", true);
    }

    @CheckPermission("UPDATE_ROLE")
    @PutMapping("/updateRole/{roleId}")
    public ResponseData<?> updateRole(@PathVariable("roleId") UUID roleId, @RequestBody Role role) {
        Role updatedRole = roleService.updateRole(roleId, role);
        return new ResponseData<>(updatedRole, true);

    }


}
