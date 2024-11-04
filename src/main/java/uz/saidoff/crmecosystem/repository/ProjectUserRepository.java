package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.ProjectUser;
import uz.saidoff.crmecosystem.entity.auth.User;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectUserRepository extends JpaRepository<ProjectUser, UUID> {

    @Query("select p.user from ProjectUser p where p.project.id=:projectId")
    List<User> findUsersByProjectId(@Param("projectId") UUID projectId);

    @Query(value = "select * from project_user where user_id=?",nativeQuery = true)
    List<ProjectUser> findByUserId(@Param("userId") UUID userId);
}
