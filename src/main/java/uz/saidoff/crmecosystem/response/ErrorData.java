package uz.saidoff.crmecosystem.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ErrorData {

    String errorMessage;
    String path;
    Integer status;
    Timestamp timestamp;

    public ErrorData(String errorMessage, String path, Integer status) {
        this.errorMessage = errorMessage;
        this.path = path;
        this.status = status;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }
}
