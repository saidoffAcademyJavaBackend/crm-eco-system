package uz.saidoff.crmecosystem.exception;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException{
    String message;
    public ForbiddenException(String message, String forbidden) {
        this.message = message + " " + forbidden;
    }
}
