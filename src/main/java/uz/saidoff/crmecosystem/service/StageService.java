package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Stage;
import uz.saidoff.crmecosystem.mapper.StageMapper;
import uz.saidoff.crmecosystem.payload.StageCreateDto;
import uz.saidoff.crmecosystem.payload.StageDto;
import uz.saidoff.crmecosystem.repository.StageRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageRepository stageRepository;
    private final StageMapper stageMapper;

    public ResponseData<StageDto> createStage(StageCreateDto createDto) {
        Stage stage = stageMapper.toEntity(createDto);
        stageRepository.save(stage);
        return ResponseData.successResponse(stageMapper.toDto(stage));
    }
}
