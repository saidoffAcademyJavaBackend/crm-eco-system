package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.InternGetDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.InternsService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interns")
public class InternsController {
    private final InternsService internsService;

    @GetMapping("/get-all")
    public ResponseData<?> getAllInterns(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return this.internsService.getAllInterns(page, size);
    }

    @GetMapping("/get-one/{interId}")
    public ResponseData<?> getOneIntern(@PathVariable UUID interId) {
        return this.internsService.getOneById(interId);
    }

    @PostMapping("add-intern/{userId}")
    public ResponseData<?> addIntern(@PathVariable UUID userId, @RequestBody InternGetDto internGetDto){
        return this.internsService.addIntern(userId,internGetDto);
    }
}
