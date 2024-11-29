package uz.saidoff.crmecosystem.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.RoomCountEquipment;
import uz.saidoff.crmecosystem.entity.RoomEquipment;
import uz.saidoff.crmecosystem.payload.GroupInfoInRoomResponse;
import uz.saidoff.crmecosystem.payload.RoomDeletedInfoResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomCountEquipmentRepository extends JpaRepository<RoomCountEquipment, UUID> {


    List<RoomCountEquipment> findAllByIdAndDeletedFalse(UUID equipmentId);
    List<RoomCountEquipment> findAllByDeletedIsFalse();

}
