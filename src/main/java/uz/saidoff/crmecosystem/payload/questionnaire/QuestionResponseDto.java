package uz.saidoff.crmecosystem.payload.questionnaire;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionResponseDto {
  private UUID id;
  private String question;
  private String description;
  private List<UUID> attachmentIds;
  private List<AnswersResponseDto> answers;
  private boolean questionnaire;
}
