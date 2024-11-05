package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.RoomAssignment;
import uz.saidoff.crmecosystem.payload.GroupInfoInRoomResponse;

import java.util.List;
import java.util.UUID;

@Repository
public interface RoomAssignmentRepository extends JpaRepository<RoomAssignment, UUID> {

    @Query("select new uz.saidoff.crmecosystem.payload.GroupInfoInRoomResponse(g.group.id, g.group.name, g.group.teacher.id, g.group.startTime, " +
            "g.group.endTime, g.group.groupType, g.group.startedDate, r.roomName, g.id) from room_assignments as g " +
            "join g.room  as r  ON  g.room.id = r.id where r.id =:roomId")
    List<GroupInfoInRoomResponse> getGroupsByRoomId(@Param("roomId") UUID roomId);



}
