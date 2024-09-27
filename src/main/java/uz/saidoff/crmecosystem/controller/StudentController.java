package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.StudentResponseDto;
import uz.saidoff.crmecosystem.payload.StudentUpdateDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.StudentService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/student-crud")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @CheckPermission("CREATE_USER")
    @PostMapping("/student-create/{groupId}")
    public ResponseEntity<ResponseData<?>> createStudent(@PathVariable UUID groupId, @RequestBody StudentResponseDto studentResponseDto) {
        ResponseData<?> responseData = studentService.saved(groupId, studentResponseDto);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 409).body(responseData);
    }

    @CheckPermission("DELETE_USER")
    @DeleteMapping("/delete-student/{studentId}")
    public ResponseEntity<ResponseData<String>> deleteStudent(@PathVariable UUID studentId) {
        ResponseData<String> responseData = studentService.removeStudent(studentId);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 400).body(responseData);
    }

    @CheckPermission("GET_USER")
    @GetMapping("/get-all-student")
    public ResponseEntity<ResponseData<?>> getAllStudentPagebl(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        ResponseData<?> responseData = studentService.getStudentGroupSorted(page, size);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 401).body(responseData);
    }

    @CheckPermission("GET_USER")
    @GetMapping("/get-student-group-filtr/{groupId}")
    public ResponseEntity<ResponseData<?>> getGroupFiltr(@PathVariable UUID groupId) {
        ResponseData<?> filtr = studentService.getFiltr(groupId);
        return ResponseEntity.status(filtr.isSuccess() ? 200 : 409).body(filtr);
    }

    @CheckPermission("EDIT_USER")
    @PatchMapping("/update-student/{studentId}")
    public ResponseEntity<ResponseData<?>> updateStudent(@PathVariable UUID studentId, @RequestBody StudentUpdateDto updateDto) {
        ResponseData<?> responseData = studentService.updateStudent(studentId, updateDto);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 409).body(responseData);
    }

    @CheckPermission("EDIT_USER")
    @PostMapping("/edit-user-transfer-intern/{userId}")
    public ResponseEntity<ResponseData<?>> userTransferToIntern(@PathVariable UUID userId) {
        ResponseData<?> responseData = studentService.userTransfer(userId);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 401).body(responseData);
    }

    @CheckPermission("GET_USER")
    @GetMapping("/get-user/{userId}")
    public ResponseEntity<ResponseData<?>> getUser(@PathVariable UUID userId) {
        ResponseData<?> responseData = studentService.getUserById(userId);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 401).body(responseData);
    }

}
