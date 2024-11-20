package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.enums.WeekDays;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TeacherValidSchedule {

    private Time startTime;
    private Time endTime;
    private Date startDate;
    private List<WeekDays> weekDays;

}
