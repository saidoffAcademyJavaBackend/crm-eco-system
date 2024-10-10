package uz.saidoff.crmecosystem.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.RoleDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.RoleService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/role")
public class RoleController {

    private final RoleService roleService;

    @CheckPermission("ADD_ROLE")
    @PostMapping("/addRole")
    public ResponseEntity<?> addRole(@RequestBody RoleDto roleDto) {
        ResponseData<?> responseData = roleService.addRole(roleDto);
        return ResponseEntity.ok(responseData);
    }

    @CheckPermission("UPDATE_ROLE")
    @PutMapping("/updateRole/{roleId}")
    public ResponseEntity<?> updateRole(@PathVariable("roleId") UUID roleId, @RequestBody RoleDto role) {
        ResponseData<?> responseData = roleService.updateRole(roleId, role);
        return ResponseEntity.ok(responseData);
    }


    @CheckPermission("GET_ROLE")
    @GetMapping("/getRole/{roleId}")
    public ResponseEntity<RoleDto> getRole(@PathVariable("roleId") UUID roleId) {
        RoleDto role = roleService.getRole(roleId);
        return ResponseEntity.ok(role);
    }

    @CheckPermission("GET_ROLE")
    @GetMapping("/getAllRoles")
    public ResponseEntity<?> getAllRolesPg(@RequestParam(name = "page", defaultValue = "0") int page,
                                           @RequestParam(name = "size", defaultValue = "10") int size) {
        ResponseData<?> allRoles = roleService.getAllRoles(page, size);
        return ResponseEntity.ok(allRoles);
    }


    @CheckPermission("GET_ROLE")
    @GetMapping("/getAllDeletedRoles")
    public ResponseEntity<?> getAllDeletedRolesPg(@RequestParam(name = "page", defaultValue = "0") int page,
                                                  @RequestParam(name = "size", defaultValue = "10") int size) {
        ResponseData<?> allDeletedRoles = roleService.getAllDeletedRoles(page, size);
        return ResponseEntity.ok(allDeletedRoles);
    }

    @CheckPermission("DELETE_ROLE")
    @DeleteMapping("/deleteRole/{roleId}")
    public ResponseData<?> removeRole(@PathVariable("roleId") UUID roleId) {
        roleService.deleteRole(roleId);
        return new ResponseData<>("Role has been successfully deleted", true);
    }


}
