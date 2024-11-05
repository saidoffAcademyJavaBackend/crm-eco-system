package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.entity.GroupType;
import uz.saidoff.crmecosystem.enums.WeekDays;

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
public class GroupInfoInRoomResponse {

    private UUID groupId;
    private String groupName;
    private UUID teacherId;
    private Time startTime;
    private Time endTime;
    private GroupType groupType;
    private Date startedDate;
//    private List<WeekDays> weekDays;

    private String roomName;

    private UUID assignmentId;

    public GroupInfoInRoomResponse() {

    }

    public GroupInfoInRoomResponse(
            UUID groupId,
            String groupName,
            UUID teacherId,
            Time startTime,
            Time endTime,
            GroupType groupType,
            Date startedDate,
//            List<WeekDays> weekDays,
            String roomName,
            UUID assignmentId) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.teacherId = teacherId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.groupType = groupType;
        this.startedDate = startedDate;
//        this.weekDays = weekDays;
        this.roomName = roomName;
        this.assignmentId = assignmentId;
    }
}
