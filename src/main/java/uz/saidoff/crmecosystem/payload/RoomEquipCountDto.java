package uz.saidoff.crmecosystem.payload;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Setter
@Getter
@Component
@AllArgsConstructor
@NoArgsConstructor
public class RoomEquipCountDto {

    private UUID id;
    private Integer count;


}
