package uz.saidoff.crmecosystem.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.saidoff.crmecosystem.entity.FileEntity;
import uz.saidoff.crmecosystem.payload.FileDownloadResponse;
import uz.saidoff.crmecosystem.payload.FileUploadRequest;
import uz.saidoff.crmecosystem.payload.FileUploadResponse;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.FileService;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;


    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FileUploadResponse> uploadFile(@Validated @RequestParam("file") MultipartFile multipartFile,
                                                         @RequestParam("name") String name) throws IOException {

        FileUploadRequest fileUploadRequest = new FileUploadRequest(multipartFile, name);

        return ResponseEntity.ok(fileService.saveFile(multipartFile));
    }

    @ApiOperation(value = "Download a file")
    @GetMapping("/download/{fileId}")
    public ResponseEntity<FileDownloadResponse> downloadResponseResponseEntity(@PathVariable UUID fileId) {
        FileDownloadResponse downloadResponse = fileService.downloadData(fileId);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.valueOf("image/png")).body(downloadResponse);
    }


    @ApiOperation(value = "Download a file")
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable UUID id) {
        Optional<FileEntity> fileEntityOptional = fileService.getFile(id);
        if (fileEntityOptional.isPresent()) {
            FileEntity fileEntity = fileEntityOptional.get();
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(fileEntity.getFileType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileEntity.getFileName() + "\"")
                    .body(fileEntity.getData());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
