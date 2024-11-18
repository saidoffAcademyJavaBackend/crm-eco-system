package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.Attachment;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.auth.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class QuestionCreateDto {
    private UUID questionId;
    private String question;
    private String description;
    private List<Attachment> attachmentIds;
    private List<AnswersDto> answers;
    private List<UUID> answerIds;
    private List<UUID> answeredQuestionIds;
    private List<Group> groups;
    private List<User> users;
}
