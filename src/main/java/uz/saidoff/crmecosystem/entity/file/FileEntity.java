package uz.saidoff.crmecosystem.entity.file;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.*;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class FileEntity extends AbsEntity {


    private String fileName;

    private String fileType;

    private Long fileSize;

    @Lob
    @Column(name = "imagesdata", length = 7777)
    private byte[] data;
}
