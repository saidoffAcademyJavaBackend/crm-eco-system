package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskCommentDto {
    private UUID taskCommentId;
    private String text;
    private UUID userId;
    private UUID taskId;
    private UUID replayId;
}
