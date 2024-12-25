package uz.saidoff.crmecosystem.payload.questionnaire;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class QuestionCreateDto {
  private String question;
  private String description;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private List<UUID> attachmentIds;
  private List<AnswersCreateDto> answers;
  private boolean questionnaire;
  private List<UUID> groupIDs;
  private List<UUID> userIDs;
}
