package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.SpecialityCreatDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.SpecialityService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/speciality")
public class SpecialityController {
    private final SpecialityService specialityService;

    @PostMapping("create_speciality")
    public ResponseData<?> createSpeciality(SpecialityCreatDto specialityCreatDto) {
        return this.specialityService.create(specialityCreatDto);
    }

    @PutMapping("speciality-update/{specialityId}")
    public ResponseData<?> updateSpeciality(@PathVariable UUID specialityId, SpecialityCreatDto specialityCreatDto) {
        return this.specialityService.update(specialityId,specialityCreatDto);
    }

    @GetMapping("/{specialityId}")
    public ResponseData<?> getSpeciality(@PathVariable UUID specialityId) {
        return this.specialityService.get(specialityId);
    }

//    @GetMapping("/get-all-speciality")
//    public ResponseData<?> getAllSpecialities(@RequestParam(defaultValue = "0") int page,
//                                              @RequestParam(defaultValue = "10") int size) {
//        return this.specialityService.getAll(page,size);
//    }
//
//    @DeleteMapping("delete-speciality/{specialityId}")
//    public ResponseData<?> deleteSpeciality(@PathVariable UUID specialityId) {
//        return this.specialityService.delete(specialityId);
//    }

}
