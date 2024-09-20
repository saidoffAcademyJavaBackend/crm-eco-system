package uz.saidoff.crmecosystem.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorData {

    String errorMessage;
    String fieldName;
    int status;
    Timestamp timestamp;
    String path;

    public ErrorData(String errorMessage, String path, Integer status) {
        this.errorMessage = errorMessage;
        this.path = path;
        this.status = status;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }
}
