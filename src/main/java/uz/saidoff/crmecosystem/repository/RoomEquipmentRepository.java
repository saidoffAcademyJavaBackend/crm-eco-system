package uz.saidoff.crmecosystem.repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.RoomEquipment;
import uz.saidoff.crmecosystem.payload.RoomDeletedInfoResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomEquipmentRepository extends JpaRepository<RoomEquipment, UUID> {

//    @Query("select new uz.saidoff.crmecosystem.payload.GroupInfoInRoomResponse(" +
//            "g.group.id, g.group.name, g.group.teacher.id, g.group.startTime, " +
//            "g.group.endTime, g.group.groupType, g.group.startedDate, r.roomName, g.id) from room_assignments as g " +
//            "join g.room  as r  ON  g.room.id = r.id where r.id =:roomId")
//    List<GroupInfoInRoomResponse> getGroupsByRoomId(@Param("roomId") UUID roomId);
//
//    Optional<RoomEquipment> findByRoomIdAndResponsiblePersonIdAndGroupId(UUID roomId, UUID personId, UUID groupId);

//    List<RoomEquipment> findAllByIdAndDeletedFalse(UUID id);
    Optional<RoomEquipment> findByIdAndDeletedFalse(UUID equipmentId);
    List<RoomEquipment> findAllByIdAndDeletedFalse(UUID equipmentId);

    List<RoomEquipment> findAllByIdAndDeletedIsFalse(UUID id);
    List<RoomEquipment> findAllByDeletedIsFalse(Pageable pageable);
    List<RoomEquipment> findAllByDeletedIsTrue(Pageable pageable);

    @Query("select new uz.saidoff.crmecosystem.payload.RoomDeletedInfoResponse(e.id, e.name, e.deletedCount) from equipments as e where e.deletedCount>0")
    List<RoomDeletedInfoResponse> getDeletedEquipments(Pageable pageable);
}
