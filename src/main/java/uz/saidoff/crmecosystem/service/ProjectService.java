package uz.saidoff.crmecosystem.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.saidoff.crmecosystem.repository.ProjectRepository;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
}
