package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.GroupStudent;
import uz.saidoff.crmecosystem.enums.WeekDays;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupStudentRepository extends JpaRepository<GroupStudent, UUID> {

    @Query("select g.groupId from GroupStudent g where g.studentId.id=:studentId")
    Optional<Group> getWeekDayByStudentId(@Param("studentId") UUID studentId);
}
