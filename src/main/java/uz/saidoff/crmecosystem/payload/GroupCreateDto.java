package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.enums.WeekDays;
import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupCreateDto {

    private String name;
    private Integer countOfStudents;
    private UUID teacherId;
    private Timestamp startTime;
    private Timestamp endTime;
    private String linkOfTelegram;
    private UUID groupTypeId;
    private List<WeekDays> weekDays;
    private List<UUID> studentsId;
    private boolean active;

}
