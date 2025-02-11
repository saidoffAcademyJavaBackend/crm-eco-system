package uz.saidoff.crmecosystem.controller;


import lombok.RequiredArgsConstructor;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import uz.saidoff.crmecosystem.entity.Attachment;
import uz.saidoff.crmecosystem.entity.AttachmentContent;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.FileService;
import uz.saidoff.crmecosystem.valid.CheckPermission;


import java.io.IOException;
import java.util.UUID;


@Validated
@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @CheckPermission("CREATE_USER")
    @PostMapping(value = "/file-upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> attachmentContentResponseEntity(@Validated @RequestParam("file") MultipartFile multipartFile) throws IOException {
        ResponseData<?> responseData = fileService.saved(multipartFile);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 400).body(responseData);
    }

    @GetMapping("/file-download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable UUID fileId) {
        ResponseData<Attachment> responseData = fileService.downloadFile(fileId);
        Attachment content = responseData.getData();
        AttachmentContent attachmentContent = content.getAttachmentContent();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(content.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + content.getFileOriginalName() + "\"")
                .body(new ByteArrayResource(attachmentContent.getMainContent()));
    }

    @DeleteMapping("/delete-images{fileId}")
    public ResponseEntity<ResponseData<?>> deleteFile(@PathVariable UUID fileId) {
        ResponseData<?> responseData = fileService.deleteFile(fileId);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 409).body(responseData);
    }

}
