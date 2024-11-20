package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.AnsweredQuestions;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class QuestionCreateDto {
    private UUID questionId;
    private String question;
    private String description;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<UUID> attachmentIds;
    private List<AnswersDto> answers;
    private List<UUID> answerIds;
    private List<AnsweredQuestions> answeredQuestionIds;
    private boolean questionnaire;
    private boolean inProcess;
    private List<UUID> groupIDs;
    private List<UUID> userIDs;
}
