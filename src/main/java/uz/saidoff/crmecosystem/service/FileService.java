package uz.saidoff.crmecosystem.service;

import com.fasterxml.jackson.databind.util.NativeImageUtil;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.impl.IOFileUploadException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.saidoff.crmecosystem.entity.FileEntity;
import uz.saidoff.crmecosystem.exception.FileNotFoundException;
import uz.saidoff.crmecosystem.exception.NotFoundException;
import uz.saidoff.crmecosystem.mapper.FileMapper;
import uz.saidoff.crmecosystem.payload.FileDownloadResponse;
import uz.saidoff.crmecosystem.payload.FileUploadRequest;
import uz.saidoff.crmecosystem.payload.FileUploadResponse;
import uz.saidoff.crmecosystem.repository.FileRepository;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.util.ImageUtils;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FileMapper mapper;

    public boolean isValidFile(MultipartFile multipartFile) {
        String contentType = multipartFile.getContentType();

        return contentType.equals("image/jpeg") || contentType.equals("image/png") || contentType.equals("text/plain");
    }

    public FileUploadResponse saveFile(MultipartFile file) throws IOException {

        FileEntity save = fileRepository.save(FileEntity.builder()
                .fileName(file.getOriginalFilename())
                .fileType(file.getContentType())
                .fileSize(file.getSize())
                .data(ImageUtils.compresImage(file.getBytes())).build()
        );

        return mapper.toFileUploadResponse(save);
    }


    public FileDownloadResponse downloadData(UUID fileId) {
        Optional<FileEntity> optionalFile = fileRepository.findById(fileId);
        if (optionalFile.isEmpty()) {
            throw new FileNotFoundException("data not found");
        }
        byte[] deCompresImages = ImageUtils.deCompresImages(optionalFile.get().getData());
        return mapper.toFIleDownloadResp(deCompresImages, optionalFile.get());
    }

    public Optional<FileEntity> getFile(UUID id) {

        Optional<FileEntity> optionalFile = fileRepository.findById(id);
        if (optionalFile.isEmpty()) {
            throw new NotFoundException("not found");
        }
        return optionalFile;
    }
}
