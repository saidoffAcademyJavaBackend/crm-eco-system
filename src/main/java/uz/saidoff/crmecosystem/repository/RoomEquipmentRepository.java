package uz.saidoff.crmecosystem.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.RoomEquipment;
import uz.saidoff.crmecosystem.payload.GroupInfoInRoomResponse;
import uz.saidoff.crmecosystem.payload.RoomDeletedInfoResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomEquipmentRepository extends JpaRepository<RoomEquipment, UUID> {


    Optional<RoomEquipment> findByIdAndDeletedFalse(UUID equipmentId);
    List<RoomEquipment> findByIdAndDeletedIsFalse(UUID equipmentId);

    List<RoomEquipment> findAllByNameAndDeletedIsFalse(String name);

    List<RoomEquipment> findAllByDeletedIsFalse(Pageable pageable);

//    @Query("select new uz.saidoff.crmecosystem.payload.RoomDeletedInfoResponse(e.id, e.name  groups as g from equipments as e")
//    List<RoomDeletedInfoResponse> getDeletedEquipments(Pageable pageable);

    @Query("select new uz.saidoff.crmecosystem.payload.GroupInfoInRoomResponse (r.group.id, r.group.name, r.group.teacher.id, " +
            "r.group.startTime, r.group.endTime, r.group.startedDate) from rooms as r INNER JOIN groups as g ON r.group.id = g.id where r.id = :roomId")
    List<GroupInfoInRoomResponse> getGroupsByRoomId(@Param("roomId") UUID roomId);
}
