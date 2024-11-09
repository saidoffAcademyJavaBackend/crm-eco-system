package uz.saidoff.crmecosystem.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Setter
@Getter
@Component
@NoArgsConstructor
@AllArgsConstructor
public class RoomDeletedInfoResponse {

    private UUID id;
    private String name;
    private UUID roomId;

}
