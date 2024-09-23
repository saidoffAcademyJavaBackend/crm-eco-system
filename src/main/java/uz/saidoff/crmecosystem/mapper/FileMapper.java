package uz.saidoff.crmecosystem.mapper;

import org.springframework.stereotype.Component;
import uz.saidoff.crmecosystem.entity.file.FileEntity;
import uz.saidoff.crmecosystem.payload.imageDto.FileDownloadResponse;
import uz.saidoff.crmecosystem.payload.imageDto.FileUploadResponse;

@Component
public class FileMapper {


    public FileUploadResponse toFileUploadResponse(FileEntity fileEntity) {
        return new FileUploadResponse(
                fileEntity.getId(),
                fileEntity.getFileName(),
                "",
                fileEntity.getFileSize(),
                fileEntity.getData()
        );
    }

    public FileDownloadResponse toFIleDownloadResp(byte[] bytes, FileEntity file) {

        return new FileDownloadResponse(file.getId(), file.getFileName(), file.getFileType(), file.getFileSize(), bytes,
                file.getCreatedAt(), file.getUpdatedAt(), file.getUpdatedBy(), file.isDeleted());
    }
}
