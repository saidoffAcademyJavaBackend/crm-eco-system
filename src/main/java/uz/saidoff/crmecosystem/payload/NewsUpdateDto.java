package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsUpdateDto {
    private UUID newsId;
    private String title;
    private String content;
    private UUID attachmentId;
    private List<UUID> roleId;
}
