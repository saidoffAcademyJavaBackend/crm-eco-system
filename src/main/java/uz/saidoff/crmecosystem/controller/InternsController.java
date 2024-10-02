package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
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
    @PostMapping("add-intern/{userId}")
    public ResponseData<?> addIntern(@PathVariable UUID userId, @RequestBody InternGetDto internGetDto) {
        return this.internsService.addIntern(userId, internGetDto);
    }

    @CheckPermission("INTERN_UPDATE")
    @PutMapping("update-intern")
    public ResponseData<?> updateIntern(@RequestBody InternGetDto internGetDto) {
        return this.internsService.update(internGetDto);
    }

    @CheckPermission("DELETE_INTERN")
    @DeleteMapping("delete-intern-by-id/{internId}")
    public ResponseData<?> deleteInternById(@PathVariable UUID internId) {
        return  this.internsService.deleteById(internId);
    }
}
