package uz.saidoff.crmecosystem.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("secured")
public class SecuredApiController {

    @GetMapping("get")
    @PreAuthorize(value = "READ_ONLY")
    public String get() {
        return "Hello from secured api";
    }
}
