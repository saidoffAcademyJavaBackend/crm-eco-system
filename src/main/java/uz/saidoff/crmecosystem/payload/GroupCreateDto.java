package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.enums.WeekDays;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupCreateDto {

    private String name;
    private UUID teacherId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startDate;
    private String linkOfTelegram;
    private UUID groupTypeId;
    private List<WeekDays> weekDays;
    private List<UUID> studentsId;
    private boolean active;
    private boolean student;
    private Double paymentAmount;

}
