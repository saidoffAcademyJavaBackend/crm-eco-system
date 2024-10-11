package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.saidoff.crmecosystem.entity.Stage;

import java.util.List;
import java.util.UUID;

public interface StageRepository extends JpaRepository<Stage, UUID> {
    List<Stage> findAllByProjectId(UUID projectId);
}
