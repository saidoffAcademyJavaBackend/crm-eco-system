package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TaskAddDto {
    private String title;
    private String description;
    private Date deadline;
    private UUID stageId;
    private int order;
    private List<UUID> attachedUsers;
}
