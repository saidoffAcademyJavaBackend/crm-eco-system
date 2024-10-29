package uz.saidoff.crmecosystem.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import uz.saidoff.crmecosystem.entity.template.AbsEntity;

import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class FileEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String fileName;

    private String fileType;

    private Long fileSize;

    @Lob
    @Column(name = "imagesdata", length = 7777)
    private byte[] data;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;


    @LastModifiedBy
    private UUID updatedBy;

    private boolean deleted;
}
