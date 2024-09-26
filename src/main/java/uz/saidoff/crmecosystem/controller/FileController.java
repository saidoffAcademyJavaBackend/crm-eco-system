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
import uz.saidoff.crmecosystem.payload.imageDto.FileDownloadResponse;
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


//    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<FileUploadResponse> uploadFile(@Validated @RequestParam("file") MultipartFile multipartFile,
//                                                         @RequestParam("name") String name) throws IOException {
//
//        FileUploadRequest fileUploadRequest = new FileUploadRequest(multipartFile, name);
//
//        return ResponseEntity.ok(fileService.saveFile(multipartFile));
//    }
//
//    @GetMapping("/download/{fileId}")
//    public ResponseEntity<FileDownloadResponse> downloadResponseResponseEntity(@PathVariable UUID fileId) {
//        FileDownloadResponse downloadResponse = fileService.downloadData(fileId);
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(downloadResponse);
//    }

    @CheckPermission("CREATE_USER")
    @PostMapping(value = "/file-upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> attachmentContentResponseEntity(@Validated @RequestParam("file") MultipartFile multipartFile) throws IOException {
        ResponseData<String> response = fileService.saved(multipartFile);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }

    @GetMapping("/file-download/{fileId}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable UUID fileId) {
        ResponseData<AttachmentContent> responseData = fileService.downloadFile(fileId);
        AttachmentContent content = responseData.getData();
        Attachment attachment = content.getAttachment();

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getFileOriginalName() + "\"")
                .body(new ByteArrayResource(content.getMainContent()));
    }

}
