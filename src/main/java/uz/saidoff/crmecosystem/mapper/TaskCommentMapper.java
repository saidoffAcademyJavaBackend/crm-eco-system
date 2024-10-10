package uz.saidoff.crmecosystem.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.TaskComment;
import uz.saidoff.crmecosystem.payload.TaskCommentCreateDto;
import uz.saidoff.crmecosystem.payload.TaskCommentDto;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TaskCommentMapper {

    public TaskComment toEntity(TaskCommentCreateDto taskCommentCreateDto) {
        TaskComment taskComment = new TaskComment();
        taskComment.setText(taskCommentCreateDto.getText());
        taskComment.setTaskId(taskCommentCreateDto.getTaskId());
        taskComment.setUserId(taskCommentCreateDto.getUserId());
        taskComment.setReplayId(taskCommentCreateDto.getReplayId());
        return taskComment;
    }

    public TaskCommentDto toDto(TaskComment taskComment) {
        TaskCommentDto taskCommentDto = new TaskCommentDto();
        taskCommentDto.setText(taskComment.getText());
        taskCommentDto.setTaskId(taskComment.getTaskId());
        taskCommentDto.setUserId(taskComment.getUserId());
        taskCommentDto.setTaskCommentId(taskComment.getId());
        taskCommentDto.setReplayId(taskComment.getReplayId());
        return taskCommentDto;
    }

    public TaskComment updateEntity(TaskComment taskComment, TaskCommentCreateDto taskCommentCreateDto) {
        if (taskCommentCreateDto.getText() != null) taskComment.setText(taskCommentCreateDto.getText());
        if (taskCommentCreateDto.getTaskId() != null) taskComment.setTaskId(taskCommentCreateDto.getTaskId());
        if (taskCommentCreateDto.getUserId() != null) taskComment.setUserId(taskCommentCreateDto.getUserId());
        if (taskCommentCreateDto.getReplayId() != null) taskComment.setId(taskCommentCreateDto.getReplayId());
        return taskComment;
    }

    public List<TaskCommentDto> toDto(List<TaskComment> taskComments) {
        List<TaskCommentDto> taskCommentDto = new ArrayList<>();
        for (TaskComment taskComment : taskComments) {
            taskCommentDto.add(toDto(taskComment));
        }
        return taskCommentDto;
    }


}
