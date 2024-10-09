package uz.saidoff.crmecosystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Project;

import java.util.UUID;

@Repository
public interface ProjectRepository extends JpaRepository<Project, UUID> {
    Page<Project> findAllByDeletedFalse(Pageable pageable);
}
