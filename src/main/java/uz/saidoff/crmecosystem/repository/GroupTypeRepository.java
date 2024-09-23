package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.saidoff.crmecosystem.entity.GroupType;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GroupTypeRepository extends JpaRepository<GroupType, UUID> {

    Optional<GroupType> findByIdAndDeletedIsFalse(UUID id);
    List<GroupType> findAllByDeletedIsFalse();
}
