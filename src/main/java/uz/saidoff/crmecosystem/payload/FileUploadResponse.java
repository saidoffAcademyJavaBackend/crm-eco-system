package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileUploadResponse {

    private UUID uploadid;

    private String name;

    private String url;

    private Long size;


}
