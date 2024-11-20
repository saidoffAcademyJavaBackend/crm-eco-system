package uz.saidoff.crmecosystem.payload;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.GroupType;
import java.sql.Time;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
public class GroupInfoInRoomResponse {


    private UUID groupId;
    private String groupName;
    private UUID teacherId;
    private Time startTime;
    private Time endTime;
    private Date startedDate;

}
