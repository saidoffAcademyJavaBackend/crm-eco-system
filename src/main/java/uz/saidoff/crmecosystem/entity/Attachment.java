package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Attachment extends AbsEntity {

    private String name;

    private String fileOriginalName;

    private long size;

    private String contentType;

    @OneToOne(mappedBy = "attachment", cascade = CascadeType.ALL)
    private AttachmentContent attachmentContent;

}
