package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AnswerQuestionsDto {
    private UUID userId;
    private UUID questionId;
    private UUID answerId;
}
