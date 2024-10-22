package uz.saidoff.crmecosystem.service;

import jakarta.transaction.Transactional;
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
import uz.saidoff.crmecosystem.repository.*;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final TaskUserRepository taskUserRepository;
    private final UserRepository userRepository;
    private final StageRepository stageRepository;

    public ResponseData<?> addTask(TaskAddDto taskAddDto) {
        Task task = taskMapper.addDtoToTask(taskAddDto);

        Optional<Stage> optionalStage = stageRepository.findById(taskAddDto.getStageId());
        if (optionalStage.isEmpty()) {
            throw new NotFoundException("Stage not found");
        }
        task.setStage(optionalStage.get());
        taskRepository.save(task);
        if (!taskAddDto.getAttachedUsers().isEmpty()) {
            TaskUser taskUser = new TaskUser();
            taskUser.setTask(task);
            List<UUID> attachedUsers = taskAddDto.getAttachedUsers();
            for (UUID attachedUser : attachedUsers) {
                Optional<User> optionalUser = userRepository.findById(attachedUser);
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
        List<Stage> stages = stageRepository.findAllByProjectId(projectId);
        if (stages.isEmpty()) {
            return ResponseData.successResponse("No stage found");
        }
        List<Task> allTasks = taskRepository.findAllByStageIn(stages);
        if (allTasks.isEmpty()) {
            return ResponseData.successResponse("No tasks found");
        }
        List<GetTaskDto> data = new LinkedList<>();
        for (Task allTask : allTasks) {
            List<User> usersByTaskId = new LinkedList<>();
            List<TaskUser> allByTask = taskUserRepository.findAllByTask(allTask);
            for (TaskUser taskUser : allByTask) {
                usersByTaskId.add(taskUser.getUser());
            }
            if (!usersByTaskId.isEmpty()) {
                data.add(taskMapper.toGetTaskDto(allTask, usersByTaskId));
            } else {
                GetTaskDto getTaskDto = new GetTaskDto();
                getTaskDto.setTask(allTask);
                data.add(getTaskDto);
            }
        }
        return ResponseData.successResponse(data);
    }

    public ResponseData<?> gorOneById(UUID taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            throw new NotFoundException("Task not found");
        }
        Task task = optionalTask.get();
        List<TaskUser> allUsersByTask = taskUserRepository.findAllByTask(task);
        GetTaskDto getTaskDto = new GetTaskDto();
        getTaskDto.setTask(task);
        if (!allUsersByTask.isEmpty()) {
            List<User> users = new LinkedList<>();
            for (TaskUser taskUser : allUsersByTask) {
                users.add(taskUser.getUser());
            }
            getTaskDto.setAttachedUsers(users);
        }
        return ResponseData.successResponse(getTaskDto);
    }

    public ResponseData<?> updateById(UUID taskId, TaskAddDto taskAddDto) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return ResponseData.successResponse("Task not found");
        }
        Optional<Stage> optionalStage = stageRepository.findById(taskAddDto.getStageId());
        if (optionalStage.isEmpty()) {
            return ResponseData.successResponse("Stage not found");
        }
        Task task = optionalTask.get();
        int previousPositionOrder = task.getTaskOrder();
        int newPositionOrder = taskAddDto.getOrder();
        if (previousPositionOrder > newPositionOrder)
            taskRepository.movingUp(newPositionOrder, previousPositionOrder, task.getStage().getId());
        if (previousPositionOrder < newPositionOrder)
            taskRepository.movingDown(newPositionOrder, previousPositionOrder, task.getStage().getId());
        task.setTitle(taskAddDto.getTitle());
        task.setDescription(taskAddDto.getDescription());
        task.setDeadline(taskAddDto.getDeadline());
        task.setTaskOrder(taskAddDto.getOrder());
        task.setStage(optionalStage.get());
        taskRepository.save(task);
        return ResponseData.successResponse("Task updated successfully");
    }

    public ResponseData<?> moveById(UUID taskId, Integer newTaskOrder, UUID stage) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) {
            return ResponseData.successResponse("Task not found");
        }
        Task task = optionalTask.get();
        Optional<Stage> optionalStage = stageRepository.findById(stage);
        if (optionalStage.isEmpty()) {
            return ResponseData.successResponse("Stage not found");
        }
        Stage newStage = optionalStage.get();
        Integer newStageOrder = newStage.getStageOrder();
        Integer previousTaskOrder = task.getTaskOrder();
        Stage previousStage = task.getStage();

        Integer previousStageOrder = previousStage.getStageOrder();
        if (Objects.equals(previousStageOrder, newStageOrder)) {
            if (previousTaskOrder > newTaskOrder)
                taskRepository.movingUp(newTaskOrder, previousTaskOrder, task.getStage().getId());
            if (previousTaskOrder < newTaskOrder)
                taskRepository.movingDown(newTaskOrder, previousTaskOrder, task.getStage().getId());
        } else {
            taskRepository.addingToOtherStage(newTaskOrder, newStage.getId());
            taskRepository.removingToOtherStage(previousTaskOrder, previousStage.getId());
        }
        return ResponseData.successResponse("Task moved successfully");
    }

    public ResponseData<?> getAllByStageId(UUID stageId) {
        List<Task> allByStageId = taskRepository.findAllByStageId(stageId);
        if (allByStageId.isEmpty()) {
            return ResponseData.successResponse("No tasks found");
        }
        List<GetTaskDto> data = new LinkedList<>();
        for (Task task : allByStageId) {
            List<User> usersByTaskId = new LinkedList<>();
            List<TaskUser> allByTask = taskUserRepository.findAllByTask(task);
            for (TaskUser taskUser : allByTask) {
                usersByTaskId.add(taskUser.getUser());
            }
            if (!usersByTaskId.isEmpty()) {
                data.add(taskMapper.toGetTaskDto(task, usersByTaskId));
            } else {
                GetTaskDto getTaskDto = new GetTaskDto();
                getTaskDto.setTask(task);
                data.add(getTaskDto);
            }
        }
        return ResponseData.successResponse(data);
    }
}
