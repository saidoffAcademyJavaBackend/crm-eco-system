package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.saidoff.crmecosystem.enums.RoleType;

import java.util.Date;
import java.util.List;
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
    private List<RoleType> roleType;
    private UUID createdBy;
    private Date createdDate;
}
