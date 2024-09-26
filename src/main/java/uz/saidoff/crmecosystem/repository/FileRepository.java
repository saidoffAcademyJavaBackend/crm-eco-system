package uz.saidoff.crmecosystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.saidoff.crmecosystem.entity.Attachment;
import uz.saidoff.crmecosystem.entity.AttachmentContent;


import java.util.UUID;

@Repository
public interface FileRepository extends JpaRepository<Attachment, UUID> {
}
