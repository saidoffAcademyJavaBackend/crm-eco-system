package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupTypeDto {

    private UUID id;
    private String specialty;
    private String description;
}
