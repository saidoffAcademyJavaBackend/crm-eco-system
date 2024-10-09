package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.ProjectRequestDto;
import uz.saidoff.crmecosystem.payload.ProjectUpdateDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.ProjectService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/project")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @CheckPermission("CREATE_PROJECT")
    @PostMapping("/create-project")
    public ResponseEntity<?> createProject(@RequestBody ProjectRequestDto projectRequestDto) {
        ResponseData<?> responseData = projectService.savedProject(projectRequestDto);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 403).body(responseData);
    }

    @CheckPermission("GET_PROJECT")
    @GetMapping("/get-project/{projectId}")
    public ResponseEntity<?> getOneProject(@PathVariable UUID projectId) {
        ResponseData<?> responseData = projectService.getByOneProject(projectId);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 403).body(responseData);
    }

    @CheckPermission("GET_PROJECT")
    @GetMapping("/get-all-project")
    public ResponseEntity<?> getAllProject(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        ResponseData<?> responseData = projectService.allProject(page, size);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 401).body(responseData);
    }

    @CheckPermission("DELETE_PROJECT")
    @DeleteMapping("/soft-deleted-project/{projectId}")
    public ResponseEntity<?> deletedProjects(@PathVariable UUID projectId) {
        ResponseData<?> responseData = projectService.removeProject(projectId);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 401).body(responseData);
    }

    @CheckPermission("UPDATE_PROJECT")
    @PatchMapping("/update-project/{projectId}")
    public ResponseEntity<?> updateProject(@PathVariable UUID projectId, @RequestBody ProjectUpdateDto updateDto) {
        ResponseData<?> responseData = projectService.editProject(projectId, updateDto);
        return ResponseEntity.ok(responseData);
    }
}
