package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Setter
@Getter
@Component
public class RoomCreateUpdateEquipDto {

    private String name;
    private int count;
}
