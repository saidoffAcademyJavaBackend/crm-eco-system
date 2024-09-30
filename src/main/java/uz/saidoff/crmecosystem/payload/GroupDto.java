package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.enums.WeekDays;
import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto {

    private UUID id;
    private String name;
    private UUID teacherId;
    private Time startTime;
    private Time endTime;
    private Date startDate;
    private String linkOfTelegram;
    private UUID groupTypeId;
    private List<WeekDays> weekDays;
    private List<UUID> studentsId;
    private boolean active;
    private boolean student;
    private Double paymentAmount;
}
