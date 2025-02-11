package uz.saidoff.crmecosystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.saidoff.crmecosystem.entity.Speciality;

import java.util.Optional;
import java.util.UUID;

public interface SpecialityRepository extends JpaRepository<Speciality, UUID> {
    Optional<Speciality> findByName(String name);
    Optional<Speciality> findByIdAndDeletedFalse(UUID id);
    Boolean existsByNameAndDeletedFalse(String name);
    @Query("SELECT s FROM Speciality s WHERE s.deleted = false")
    Page<Speciality> findAllDeletedFalse(Pageable pageable);
}
