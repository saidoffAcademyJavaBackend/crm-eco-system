package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Setter
@Getter
@Component
public class RoomEquipCountDto {

    private UUID id;
    private Integer count;
}
