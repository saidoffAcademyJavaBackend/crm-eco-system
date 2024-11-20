package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.enums.WeekDays;

import java.sql.Time;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RequestWeekDayStudentDTO {

    private UUID studentId;
    private String groupName;
    private List<WeekDays> days;
    private Time startTime;
    private Time endTime;
    private int count;
}
