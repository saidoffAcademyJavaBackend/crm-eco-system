package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.TaskCommentCreateDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.TaskCommentService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task-comment")
public class TaskCommentController {

    private final TaskCommentService taskCommentService;

    @PostMapping("create-task-comment")
    public ResponseData<?> createTaskComment(TaskCommentCreateDto taskCommentCreateDto) {
        return this.taskCommentService.create(taskCommentCreateDto);
    }

    @GetMapping("/{taskCommentId}")
    public ResponseData<?> getTaskComment(@PathVariable UUID taskCommentId) {
        return this.taskCommentService.get(taskCommentId);
    }

    @PutMapping("update-task-comment/{taskCommentId}")
    public ResponseData<?> updateTaskComment(@PathVariable UUID taskCommentId, TaskCommentCreateDto taskCommentCreateDto) {
        return this.taskCommentService.update(taskCommentId, taskCommentCreateDto);
    }

    @DeleteMapping("delete-task-comment/{taskCommentId}")
    public ResponseData<?> deleteTaskComment(@PathVariable UUID taskCommentId) {
        return this.taskCommentService.delete(taskCommentId);
    }

    @GetMapping("/get-all-task-comment")
    public ResponseData<?> getAllTaskComments(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "10") int size) {
        return this.taskCommentService.getAll(page,size);
    }

}
