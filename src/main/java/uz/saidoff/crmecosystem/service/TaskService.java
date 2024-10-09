package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.Stage;
import uz.saidoff.crmecosystem.entity.Task;
import uz.saidoff.crmecosystem.entity.TaskUser;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.TaskMapper;
import uz.saidoff.crmecosystem.payload.GetTaskDto;
import uz.saidoff.crmecosystem.payload.TaskAddDto;
import uz.saidoff.crmecosystem.repository.TaskRepository;
import uz.saidoff.crmecosystem.repository.TaskUserRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskUserRepository taskUserRepository;
    private final UserRepository userRepository;

    public ResponseData<?> addTask(TaskAddDto taskAddDto) {
        Task task = taskMapper.addDtoToTask(taskAddDto);
        //Stage id boyich atopib tekshirish kk
        Task save = taskRepository.save(task);
        if (!taskAddDto.getAttachedUsers().isEmpty()) {
            TaskUser taskUser = new TaskUser();
            taskUser.setTask(task);
            List<UUID> attachedUsers = taskAddDto.getAttachedUsers();
            for (int i = 0; i < attachedUsers.size(); i++) {
                Optional<User> optionalUser = userRepository.findById(attachedUsers.get(i));
                if (optionalUser.isEmpty()) {
                    throw new NotFoundException("User not found");
                }
                taskUser.setUser(optionalUser.get());
                taskUserRepository.save(taskUser);
            }
        }
        return ResponseData.successResponse("Task added successfully");
    }

    public ResponseData<?> getAllByProjectId(UUID projectId) {
        // project id bo'yicha hamma stagelarni topib olib keilsh va shularning listi bo'yich hamma tasklarni olib kelish
        List<Stage> stages = new LinkedList<>();
        List<Task> allTasks = taskRepository.findAllByStageIn(stages);
        if (allTasks.isEmpty()) {
            return ResponseData.successResponse("No tasks found");
        }
        List<GetTaskDto> data = new LinkedList<>();
        for (int i = 0; i < allTasks.size(); i++) {
            List<User> usersByTaskId = taskUserRepository.findAllByTask(allTasks.get(i));
            if (!usersByTaskId.isEmpty()) {
                data.add(taskMapper.toGetTaskDto(allTasks.get(i),usersByTaskId));
            } else {
                GetTaskDto getTaskDto = new GetTaskDto();
                getTaskDto.setTask(allTasks.get(i));
                data.add(getTaskDto);
            }
        }
        return ResponseData.successResponse(data);
    }

    public ResponseData<?> gorOneById(UUID taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return ResponseData.successResponse("Task not found");
        }
        GetTaskDto data = new GetTaskDto();
        Task task = optionalTask.get();
        List<User> allByTask = taskUserRepository.findAllByTask(task);
        if (!allByTask.isEmpty()) {
            data.setTask(task);
            data.setAttachedUsers(allByTask);
        } else {
            data.setTask(task);
        }
        return ResponseData.successResponse(data);
    }
}
