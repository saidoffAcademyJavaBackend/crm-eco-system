package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
public class GroupInfoInRoomResponse {


    private UUID groupId;
    private String groupName;
    private UUID teacherId;
    private LocalTime startTime;
    private LocalTime endTime;
    private LocalDate startedDate;

}
