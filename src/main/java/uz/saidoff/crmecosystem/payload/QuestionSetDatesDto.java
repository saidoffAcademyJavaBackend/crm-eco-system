package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionSetDatesDto {

    private LocalDateTime startDate;
    private LocalDateTime endDate;

}
