package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.saidoff.crmecosystem.entity.GroupStudent;
import java.util.UUID;

public interface GroupStudentRepository extends JpaRepository<GroupStudent, UUID> {
}
