package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StageDto {

    private UUID stageId;
    private String name;
    private Integer order;
    private UUID projectId;
    private boolean done;
}