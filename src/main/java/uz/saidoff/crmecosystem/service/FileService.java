package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


import uz.saidoff.crmecosystem.entity.Attachment;
import uz.saidoff.crmecosystem.entity.AttachmentContent;

import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.FileMapper;
import uz.saidoff.crmecosystem.repository.AttachmentContentRepository;

import uz.saidoff.crmecosystem.repository.AttachmentRepository;
import uz.saidoff.crmecosystem.response.ResponseData;


import java.io.IOException;
import java.util.Optional;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class FileService {


    private final AttachmentRepository fileRepository;
    private final AttachmentContentRepository attachmentContentRepository;
    private FileMapper fileMapper;


    public boolean isValidFile(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();

        return contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("text/plain")
                || contentType.equals("application/pdf") || contentType.equals("image/gif") || contentType.equals("application/zip") ||
                contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    @Transactional
    public ResponseData<String> saved(MultipartFile multipartFile) throws IOException {
        AttachmentContent attachmentContent = new AttachmentContent();

        if (!isValidFile(multipartFile)) {
            throw new IllegalArgumentException("file is not compatible");
        }
        //byte[] bytes = ImageUtils.compresImage(multipartFile.getBytes());

        Attachment attachment = new Attachment();

        attachment.setName(multipartFile.getName());
        attachment.setFileOriginalName(multipartFile.getOriginalFilename());
        attachment.setContentType(multipartFile.getContentType());
        attachment.setSize(multipartFile.getSize());

        Attachment saved = fileRepository.save(attachment);

        attachmentContent.setMainContent(multipartFile.getBytes());
        attachmentContent.setAttachment(saved);
        attachmentContentRepository.save(attachmentContent);

        return ResponseData.successResponse("succesfuly file upload");

    }


    public ResponseData<Attachment> downloadFile(UUID fileId) {
        Optional<Attachment> optionalAttachment = fileRepository.findById(fileId);
        if (optionalAttachment.isEmpty()) {
            throw new NotFoundException("file not found ");
        }
        return ResponseData.successResponse(optionalAttachment.get(), userId);
    }

    public ResponseData<?> deleteFile(UUID fileId) {
        Optional<Attachment> optionalAttachment = fileRepository.findById(fileId);
        if (optionalAttachment.isEmpty()) {
            return new ResponseData<>("file not found", false);
        }
        Attachment attachment = optionalAttachment.get();
        fileRepository.deleteById(attachment.getId());

        return ResponseData.successResponse("file succesfuly deleted");
    }
}
