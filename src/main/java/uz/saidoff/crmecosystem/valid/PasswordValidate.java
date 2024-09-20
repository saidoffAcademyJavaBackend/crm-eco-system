package uz.saidoff.crmecosystem.valid;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidation.class)
@Documented
public @interface PasswordValidate {

    String message() default  "Parolda kamida bitta katta harf, bitta kichik harf, bitta raqam va !@#$%^&*()_+= bo'lishi kerak";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
}
