package uz.saidoff.crmecosystem.mapper;

import uz.saidoff.crmecosystem.entity.AttachmentContent;
import uz.saidoff.crmecosystem.payload.imageDto.FileDownloadResponse;

public class FileMapper {

    public FileDownloadResponse toFileResponse(AttachmentContent attachmentContent) {

        FileDownloadResponse fileDownloadResponse = new FileDownloadResponse();
        fileDownloadResponse.setName(attachmentContent.getAttachment().getName());
        fileDownloadResponse.setFileOriginalName(attachmentContent.getAttachment().getFileOriginalName());
        fileDownloadResponse.setContentType(attachmentContent.getAttachment().getContentType());
        fileDownloadResponse.setMainContent(attachmentContent.getMainContent());
        return fileDownloadResponse;
    }
}
