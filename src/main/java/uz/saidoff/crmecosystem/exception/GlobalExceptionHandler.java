package uz.saidoff.crmecosystem.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import uz.saidoff.crmecosystem.response.ErrorData;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.util.MessageKey;
import uz.saidoff.crmecosystem.util.MessageService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseData<ErrorData>> handleException(MethodArgumentNotValidException exception, WebRequest request){
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
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ResponseData<ErrorData>> handleException(AlreadyExistException exception, WebRequest request){
        LOGGER.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ResponseData.errorResponse(
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ResponseData<ErrorData>> handlingException(AccessDeniedException exception, WebRequest webRequest){
        LOGGER.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ResponseData.errorResponse(
                webRequest.getDescription(false),
                exception.getMessage(), HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccountStatusException.class)
    public ResponseEntity<ResponseData<ErrorData>> handlingException(AccountStatusException exception, WebRequest webRequest){
        LOGGER.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ResponseData.errorResponse(
                webRequest.getDescription(false),
                exception.getMessage(), HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseData<ErrorData>> handlingException(BadCredentialsException exception, WebRequest webRequest){
        LOGGER.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ResponseData.errorResponse(
                webRequest.getDescription(false),
                MessageService.getMessage(MessageKey.BAD_CREDENTIALS_EXCEPTION), HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseData<ErrorData>> handleException(Exception exception, WebRequest request){
        LOGGER.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ResponseData.errorResponse(
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ResponseData<ErrorData>> handleException(ForbiddenException exception, WebRequest request){
        LOGGER.error(exception.getMessage(), exception);
        return new ResponseEntity<>(ResponseData.errorResponse(
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);

    }
}
