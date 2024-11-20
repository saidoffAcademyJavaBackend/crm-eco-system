package uz.saidoff.crmecosystem.payload;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class RoomEquipCreateUpdateDto {

    private String name;
    private Integer totalNumber;
}
