package uz.saidoff.crmecosystem.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import uz.saidoff.crmecosystem.response.ErrorData;
import uz.saidoff.crmecosystem.response.ResponseData;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData<ErrorData>> handleException(MethodArgumentNotValidException exception, WebRequest request){
        LOGGER.error(exception.getMessage(), exception);
        List<ErrorData> errors = new ArrayList<>();
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(fieldError -> errors.add(new ErrorData(
                fieldError.getDefaultMessage(),
                fieldError.getField(),
                HttpStatus.BAD_REQUEST.value(),
                Timestamp.valueOf(LocalDateTime.now()),
                request.getDescription(false))));
        return new ResponseEntity<>(ResponseData.errorResponse(errors), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResponseData<ErrorData>> handleException(NotFoundException exception, WebRequest request){
        LOGGER.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ResponseData.errorResponse(
                request.getDescription(false),
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }

}
