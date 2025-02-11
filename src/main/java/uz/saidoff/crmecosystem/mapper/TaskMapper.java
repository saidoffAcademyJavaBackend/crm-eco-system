package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.Task;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.payload.GetTaskDto;
import uz.saidoff.crmecosystem.payload.TaskAddDto;
import uz.saidoff.crmecosystem.repository.StageRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskMapper {
    private final StageRepository stageRepository;
    public Task addDtoToTask(TaskAddDto taskAddDto) {
        Task task = new Task();
        task.setTitle(taskAddDto.getTitle());
        task.setTaskOrder(taskAddDto.getOrder());
        if (taskAddDto.getDescription() != null)
            task.setDescription(taskAddDto.getDescription());
        if (taskAddDto.getDeadline() != null)
            task.setDeadline(taskAddDto.getDeadline());

        return task;
    }

    public GetTaskDto toGetTaskDto(Task task, List<User> users) {
        GetTaskDto getTaskDto = new GetTaskDto();
        getTaskDto.setTask(task);
        getTaskDto.setAttachedUsers(users);
        return getTaskDto;
    }
}
