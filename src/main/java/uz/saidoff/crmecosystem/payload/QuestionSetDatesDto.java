package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class QuestionSetDatesDto {

    private UUID questionId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
