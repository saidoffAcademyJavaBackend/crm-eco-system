package uz.saidoff.crmecosystem.payload.questionnaire;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AnswersCreateDto {
    private String value;
    private Boolean isRightAnswer;
}
