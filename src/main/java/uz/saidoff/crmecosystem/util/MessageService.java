package uz.saidoff.crmecosystem.util;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageService {


    public static String getMessage(String message) {

        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages/message");
        return messageSource.getMessage(message, null, Locale.getDefault());
    }
}
