package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.saidoff.crmecosystem.entity.Task;
import uz.saidoff.crmecosystem.entity.TaskUser;
import uz.saidoff.crmecosystem.entity.auth.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskUserRepository extends JpaRepository<TaskUser, UUID> {
//    List<User> findAllByUser(Task task);
    List<TaskUser> findAllByTask(Task task);
}
