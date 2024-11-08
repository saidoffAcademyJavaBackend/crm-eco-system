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
@AllArgsConstructor
@NoArgsConstructor
public class RoomDeletedInfoResponse {

    private UUID id;
    private String name;
    private int deletedCount = 0;
    private UUID roomId;


    public RoomDeletedInfoResponse(UUID id, String name, int deletedCount) {
        this.id = id;
        this.name = name;
        this.deletedCount = deletedCount;
    }
}
