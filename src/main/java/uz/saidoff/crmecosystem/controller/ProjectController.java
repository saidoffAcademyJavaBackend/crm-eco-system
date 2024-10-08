package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.saidoff.crmecosystem.service.ProjectService;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;
}
