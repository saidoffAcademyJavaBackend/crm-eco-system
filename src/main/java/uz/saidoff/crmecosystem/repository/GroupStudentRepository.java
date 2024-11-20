package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.GroupStudent;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.WeekDays;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupStudentRepository extends JpaRepository<GroupStudent, UUID> {

    @Query("select g.groupId from GroupStudent g where g.studentId.id=:studentId")
    Optional<Group> getWeekDayByStudentId(@Param("studentId") UUID studentId);

    @Query("select u.studentId from GroupStudent u where u.studentId.id=:userId")
    Optional<User> getUserByGroup(@Param("userId") UUID userId);

    @Query("select u.groupId from GroupStudent u where u.studentId.id=:userId")
    Optional<Group> getGroupByUser(@Param("userId") UUID userId);
    @Query(value = "select * from group_student where student_id_id=?", nativeQuery = true)
    List<GroupStudent> findByStudentId(UUID studentId);
}
