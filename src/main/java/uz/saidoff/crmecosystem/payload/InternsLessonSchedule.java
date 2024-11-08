package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.enums.WeekDays;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InternsLessonSchedule {
    private Time startTime;

    private Time endTime;

    private List<WeekDays> weekDays;

    private Date startedDate;
}
