package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.GroupStudent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    Optional<Group> findByIdAndDeletedIsFalse(UUID id);

    List<Group> findAllByDeletedIsFalse();

    @Query("select count (size(gs) )from groups s join s.groupStudents gs where s.id=:groupId")
    int countStudentsGroup(@Param("groupId") UUID groupId);

    @Query("select g from groups g join g.groupStudents gs join gs.student u where u.id=:userId ")
    List<Group> getStudentGroups(@Param("userId") UUID userId);

    @Query("select groups from groups g where g.id=:id")
    Optional<Group> findByStudentIdPassportSeries(@Param("id") UUID id);

    @Query("select g from groups g join g.groupStudents gs where gs.student.id=:studentId")
    Optional<Group> usergaTegishliGroup(@Param("studentId") UUID studentId);


}
