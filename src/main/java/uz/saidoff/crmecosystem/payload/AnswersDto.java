package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AnswersDto {
    private UUID id;
    private String value;
    private Boolean isRightAnswer;
}
