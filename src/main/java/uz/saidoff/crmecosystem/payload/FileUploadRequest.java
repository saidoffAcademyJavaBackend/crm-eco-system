package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileUploadRequest {

    private MultipartFile file;

    private String name;
}
