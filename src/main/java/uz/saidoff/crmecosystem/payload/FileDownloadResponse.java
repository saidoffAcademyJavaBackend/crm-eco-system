package uz.saidoff.crmecosystem.payload;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.LastModifiedBy;

import java.sql.Timestamp;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileDownloadResponse {

    private UUID id;

    private String fileName;

    private String fileType;

    private Long fileSize;


    private byte[] data;

    @CreationTimestamp
    private Timestamp createdAt;

    @UpdateTimestamp
    private Timestamp updatedAt;


    @LastModifiedBy
    private UUID updatedBy;

    private boolean deleted;
}
