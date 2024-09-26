package uz.saidoff.crmecosystem.payload.imageDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileDownloadResponse {

    private String name;

    private String fileOriginalName;

    private long size;

    private String contentType;

    private byte[] mainContent;
}
