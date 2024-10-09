package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.TaskAddDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.TaskService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {
    private final TaskService taskService;

    @CheckPermission("CREATE_TASK")
    @PostMapping("/add")
    public ResponseData<?> addTask(@RequestBody TaskAddDto taskAddDto) {
        return this.taskService.addTask(taskAddDto);
    }

    @CheckPermission("GET_TASK")
    @GetMapping("/get-all-by-project-id/{projectId}")
    public ResponseData<?> getAllTaskByProjectId(@PathVariable("projectId") UUID projectId) {
        return this.taskService.getAllByProjectId(projectId);
    }
    @CheckPermission("GET_TASK")
    @GetMapping("/get-one-by-id/{taskId}")
    public ResponseData<?> getOneTaskById(@PathVariable("taskId") UUID taskId) {
        return this.taskService.gorOneById(taskId);
    }
}
