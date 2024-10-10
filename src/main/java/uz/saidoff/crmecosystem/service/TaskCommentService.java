package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.entity.TaskComment;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.TaskCommentMapper;
import uz.saidoff.crmecosystem.payload.TaskCommentCreateDto;
import uz.saidoff.crmecosystem.repository.TaskCommentRepository;
import uz.saidoff.crmecosystem.repository.TaskRepository;
import uz.saidoff.crmecosystem.repository.UserRepository;
import uz.saidoff.crmecosystem.response.ResponseData;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TaskCommentService {

    private final TaskCommentRepository taskCommentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final TaskCommentMapper taskCommentMapper;

    public ResponseData<?> create(TaskCommentCreateDto taskCommentCreateDto) {
        boolean extentUser = this.userRepository.existsById(taskCommentCreateDto.getUserId());
        if (!extentUser) {
            throw new NotFoundException("User not found");
        }
        boolean extentTask = this.taskRepository.existsById(taskCommentCreateDto.getTaskId());
        if (!extentTask) {
            throw new NotFoundException("Task not found");
        }
        TaskComment taskComment = this.taskCommentMapper.toEntity(taskCommentCreateDto);
        this.taskCommentRepository.save(taskComment);
        return ResponseData.successResponse(taskComment);
    }

    public ResponseData<?> get(UUID taskCommentId) {
        Optional<TaskComment> taskComment = this.taskCommentRepository.findByTaskIdAndDeletedFalse(taskCommentId);
        if (taskComment.isEmpty()) {
            throw new NotFoundException("Task comment not found");
        }
        return ResponseData.successResponse(this.taskCommentMapper.toDto(taskComment.get()));
    }

    public ResponseData<?> update(UUID taskCommentId, TaskCommentCreateDto taskCommentCreateDto) {
        Optional<TaskComment> taskCommentOptional = this.taskCommentRepository.findByTaskIdAndDeletedFalse(taskCommentId);
        if (taskCommentOptional.isEmpty()) {
            throw new NotFoundException("Task comment not found");
        }
        TaskComment taskComment = this.taskCommentMapper.updateEntity(taskCommentOptional.get(), taskCommentCreateDto);
        this.taskCommentRepository.save(taskComment);
        return ResponseData.successResponse("success");
    }

    public ResponseData<?> delete(UUID taskCommentId) {
        Optional<TaskComment> taskCommentOptional = this.taskCommentRepository.findByTaskIdAndDeletedFalse(taskCommentId);
        if (taskCommentOptional.isEmpty()) {
            throw new NotFoundException("Task comment not found");
        }
        TaskComment taskComment = taskCommentOptional.get();
        taskComment.setDeleted(true);
        this.taskCommentRepository.save(taskComment);
        return ResponseData.successResponse("success");
    }

    public ResponseData<?> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TaskComment> taskComments = this.taskCommentRepository.findAllDeletedFalse(pageable);
        if (taskComments.isEmpty()) {
            throw new NotFoundException("Task comment not found");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("data", this.taskCommentMapper.toDto(taskComments.toList()));
        response.put("total", taskComments.getTotalElements());
        response.put("totalPages", taskComments.getTotalPages());

        return ResponseData.successResponse(response);
    }
}
