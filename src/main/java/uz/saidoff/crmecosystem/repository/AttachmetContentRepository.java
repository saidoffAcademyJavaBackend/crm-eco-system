package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.AttachmentContent;

import java.util.UUID;

@Repository
public interface AttachmetContentRepository extends JpaRepository<AttachmentContent, UUID> {
}
