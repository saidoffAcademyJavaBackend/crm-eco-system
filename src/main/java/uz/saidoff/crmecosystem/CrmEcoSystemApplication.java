package uz.saidoff.crmecosystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CrmEcoSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmEcoSystemApplication.class, args);
    }

}
