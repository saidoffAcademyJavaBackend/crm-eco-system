package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Stage;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.payload.StageCreateDto;
import uz.saidoff.crmecosystem.payload.StageDto;
import uz.saidoff.crmecosystem.repository.ProjectRepository;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

@Component
@RequiredArgsConstructor
public class StageMapper {

    private final ProjectRepository projectRepository;

    public  Stage toEntity(StageCreateDto createDto){
        Stage stage = new Stage();
        stage.setName(createDto.getName());
        stage.setStageOrder(createDto.getOrder());
        stage.setDone(false);
        stage.setProject(projectRepository.findById(createDto.getProjectId()).orElseThrow(
                () -> new NotFoundException(MessageService.getMessage(MessageKey.PROJECT_NOT_FOUND))));

        return stage;
    }

    public StageDto toDto(Stage stage) {
        StageDto stageDto = new StageDto();
        stageDto.setStageId(stage.getId());
        stageDto.setProjectId(stage.getProject().getId());
        stageDto.setName(stage.getName());
        stageDto.setOrder(stage.getStageOrder());
        stageDto.setDone(stage.isDone());
        return stageDto;
    }
}
