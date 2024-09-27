package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.EmployeeCreatDto;
import uz.saidoff.crmecosystem.payload.EmployeeDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.EmployeeService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @CheckPermission("CREATE_USER")
    @PostMapping("employee-user")
    public ResponseData<?> createEmployee(EmployeeCreatDto employeeCreatDto) {
        return this.employeeService.create(employeeCreatDto);
    }

    @CheckPermission("EDIT_USER")
    @PutMapping("employee-update/{employeeId}")
    public ResponseData<?> updateEmployee(@PathVariable UUID employeeId, EmployeeCreatDto employeeCreatDto) {
        return this.employeeService.update(employeeId,employeeCreatDto);
    }

    @CheckPermission("GET_USER")
    @GetMapping("/{employeeId}")
    public ResponseData<EmployeeDto> getEmployee(@PathVariable UUID employeeId) {
        return this.employeeService.getUser(employeeId);
    }

    @CheckPermission("GET_USER")
    @GetMapping("/get-all-employee")
    public ResponseData<?> getEmployee(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return this.employeeService.getAllUser(page,size);
    }

    @CheckPermission("DELETE_USER")
    @DeleteMapping("delete-employee/{employeeId}")
    public ResponseData<?> deleteEmployee(@PathVariable UUID employeeId) {
        return this.employeeService.deleteEmployee(employeeId);
    }

}
