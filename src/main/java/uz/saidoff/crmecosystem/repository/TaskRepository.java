package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.saidoff.crmecosystem.entity.Stage;
import uz.saidoff.crmecosystem.entity.Task;

import java.util.List;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {
    List<Task> findAllByStageIn(List<Stage> stages);
}
