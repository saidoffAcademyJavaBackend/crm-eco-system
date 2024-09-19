package uz.saidoff.crmecosystem.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseData<T> {

    String message;
    T data;
    boolean success;
    List<ErrorData> errorData;

    public ResponseData(Boolean success){
        this.success = success;
    }
    public ResponseData(String message, Boolean success){
        this.message = message;
        this.success = success;
    }

    public ResponseData(T data, Boolean success){
        this.data = data;
        this.success = success;
    }

    public ResponseData(String path, String message, Integer errorCode){
        this.success = false;
        this.errorData = Collections.singletonList(new ErrorData(path, message, errorCode));
    }

    public ResponseData(List<ErrorData>  errorData){
        this.success = false;
        this.errorData = errorData;
    }

    public static <E> ResponseData<E> successResponse(E data){
        return new ResponseData<>(data, true);
    }
    public static ResponseData<String> successResponse(String message){
        return new ResponseData<>(message, true);
    }


    public static ResponseData<ErrorData> errorResponse(String path, String message, Integer errorCode) {
        return new ResponseData<>(path, message, errorCode);

    }
    public static ResponseData<ErrorData> errorResponse(List<ErrorData> errors){
        return new ResponseData<>(errors);
    }

}
