package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uz.saidoff.crmecosystem.entity.GroupStudent;

import java.util.List;
import java.util.UUID;

@Repository
public interface GroupStudentRepository extends JpaRepository<GroupStudent, UUID> {

    @Query("select g.groupId from GroupStudent g where g.studentId.id=:studentId")
    Optional<Group> getWeekDayByStudentId(@Param("studentId") UUID studentId);
    @Query(value = "select * from group_student where student_id_id=?", nativeQuery = true)
    List<GroupStudent> findByStudentId(UUID studentId);
}
