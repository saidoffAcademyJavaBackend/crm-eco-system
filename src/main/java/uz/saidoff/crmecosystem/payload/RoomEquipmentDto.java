package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Setter
@Getter
@Component
public class RoomEquipmentDto {

    private UUID id;
    private String name;
    private Integer count;
}
