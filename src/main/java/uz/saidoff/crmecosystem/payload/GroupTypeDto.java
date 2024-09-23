package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupTypeDto {

    private UUID id;
    private String specialty;
    private String description;
}
