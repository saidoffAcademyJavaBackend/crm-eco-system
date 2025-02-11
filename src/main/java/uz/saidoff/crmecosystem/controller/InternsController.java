package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.InternAddDto;
import uz.saidoff.crmecosystem.payload.InternGetDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.InternsService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interns")
public class InternsController {
    private final InternsService internsService;

    @CheckPermission("GET_INTERN")
    @GetMapping("/get-all")
    public ResponseData<?> getAllInterns(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return this.internsService.getAllInterns(page, size);
    }

    @CheckPermission("GET_INTERN")
    @GetMapping("/get-one/{interId}")
    public ResponseData<?> getOneIntern(@PathVariable UUID interId) {
        return this.internsService.getOneById(interId);
    }

    @CheckPermission("CREATE_INTERN")
    @PostMapping("add-intern")
    public ResponseData<?> addIntern(@RequestParam(required = false) UUID groupId, @RequestBody InternAddDto internAddDto) {
        return this.internsService.addIntern(groupId, internAddDto);
    }

    @CheckPermission("UPDATE_INTERN")
    @PutMapping("update-intern")
    public ResponseData<?> updateIntern(@RequestParam(required = false) String password, @RequestBody InternGetDto internGetDto) {
        return this.internsService.update(internGetDto, password);
    }

    @CheckPermission("DELETE_INTERN")
    @DeleteMapping("delete-intern-by-id/{internId}")
    public ResponseData<?> deleteInternById(@PathVariable UUID internId) {
        return this.internsService.deleteById(internId);
    }

    @CheckPermission("UPDATE_INTERN")
    @PutMapping("intern-to-employee/{internId}")
    public ResponseData<?> internToEmployee(@PathVariable UUID internId) {
        return this.internsService.internToEmployee(internId);
    }

    @CheckPermission("GET_INTERN")
    @GetMapping("get-projects")
    public ResponseData<?> getParticipatedProjects(@RequestParam(required = false) UUID userId) {
        return this.internsService.getParticipatedProjects(userId);
    }

    @CheckPermission("GET_INTERN")
    @GetMapping("get-groups")
    public ResponseData<?> getGroups(@RequestParam(required = false) UUID userId) {
        return this.internsService.getGroups(userId);
    }
}
