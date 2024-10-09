package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProjectResponseDto {

    private UUID projectId;
    private String name;
    private Date startDate;
    private Date endDate;
    private UUID ownerId;
    private Timestamp createdAt;


}
