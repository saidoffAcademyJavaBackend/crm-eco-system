package uz.saidoff.crmecosystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Project;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Page<Project> findAllByDeletedFalse(Pageable pageable);

    @Query("select p from Project p where DATE(p.createdAt) between :startDate and :endDate")
    List<Project> findProjectsByDate(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
