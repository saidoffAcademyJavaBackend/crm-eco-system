package uz.saidoff.crmecosystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.saidoff.crmecosystem.entity.TaskComment;

import java.util.Optional;
import java.util.UUID;

public interface TaskCommentRepository extends JpaRepository<TaskComment, UUID> {
    Optional<TaskComment> findByTaskIdAndDeletedFalse(UUID taskId);
    Page<TaskComment> findAllAndDeletedFalse(Pageable pageable);
}
