package uz.saidoff.crmecosystem.payload.imageDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileUploadResponse {

    private UUID uploadId;

    private String name;

    private String url;

    private Long size;

    private byte[] bytes;


}
