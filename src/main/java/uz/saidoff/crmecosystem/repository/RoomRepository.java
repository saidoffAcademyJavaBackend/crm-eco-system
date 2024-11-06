package uz.saidoff.crmecosystem.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Room;
import uz.saidoff.crmecosystem.payload.GroupInfoInRoomResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    Optional<Room> findByRoomNameAndDeletedFalse(String roomName);
    Optional<Room> findRoomByIdAndDeletedFalse(UUID roomId);
    List<Room> findAllByDeletedIsFalse(Pageable pageable);
    List<Room> findAllByDeletedIsTrue(Pageable pageable);

    }
