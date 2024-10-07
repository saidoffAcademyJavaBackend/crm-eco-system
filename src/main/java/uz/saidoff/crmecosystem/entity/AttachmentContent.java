package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class AttachmentContent extends AbsEntity {

    @Lob()
    private byte[] mainContent;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Attachment attachment;
}
