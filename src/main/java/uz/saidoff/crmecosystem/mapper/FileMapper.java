package uz.saidoff.crmecosystem.mapper;

import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.FileEntity;
import uz.saidoff.crmecosystem.payload.FileDownloadResponse;
import uz.saidoff.crmecosystem.payload.FileUploadRequest;
import uz.saidoff.crmecosystem.payload.FileUploadResponse;

import java.io.IOException;

@Component
public class FileMapper {


    public FileUploadResponse toFileUploadResponse(FileEntity fileEntity) {
        return new FileUploadResponse(
                fileEntity.getId(),
                fileEntity.getFileName(),
                "",
                fileEntity.getFileSize()
        );
    }

    public FileDownloadResponse toFIleDownloadResp(byte[] bytes, FileEntity file) {

        return new FileDownloadResponse(file.getId(), file.getFileName(), file.getFileType(), file.getFileSize(), bytes,
                file.getCreatedAt(), file.getUpdatedAt(), file.getUpdatedBy(), file.isDeleted());
    }
}
