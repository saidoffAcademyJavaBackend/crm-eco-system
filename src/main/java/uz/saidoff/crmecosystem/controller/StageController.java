package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.saidoff.crmecosystem.payload.StageCreateDto;
import uz.saidoff.crmecosystem.payload.StageDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.StageService;

@RestController
@RequestMapping("api/stage")
@RequiredArgsConstructor
public class StageController {

    private final StageService stageService;

    @PostMapping("create-stage")
    private ResponseData<StageDto> createStage(@RequestBody StageCreateDto createDto) {
        return stageService.createStage(createDto);
    }
}
