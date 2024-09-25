package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsGetByUserIdDto {
    private UUID id;
    private String title;
    private String content;
    private UUID attachmentId;
    private String roleType;
    private UUID createdBy;
    private Date createdDate;
}
