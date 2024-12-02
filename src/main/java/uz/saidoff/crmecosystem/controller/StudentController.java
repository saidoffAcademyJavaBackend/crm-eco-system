package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.StudentResponseDto;
import uz.saidoff.crmecosystem.payload.StudentUpdateDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.StudentService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.text.ParseException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/student-crud")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;

    @CheckPermission("CREATE_STUDENT")
    @PostMapping("/student-create")
    public ResponseEntity<ResponseData<?>> createStudent(@RequestBody StudentResponseDto studentResponseDto) throws ParseException {
        ResponseData<?> responseData = studentService.saved(studentResponseDto);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 409).body(responseData);
    }

    @CheckPermission("DELETE_STUDENT")
    @DeleteMapping("/delete-student/{studentId}")
    public ResponseEntity<ResponseData<String>> deleteStudent(@PathVariable UUID studentId) {
        ResponseData<String> responseData = studentService.removeStudent(studentId);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 400).body(responseData);
    }

    @CheckPermission("GET_STUDENT")
    @GetMapping("/get-all-student")
    public ResponseEntity<ResponseData<?>> getAllStudentPagebl(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {
        ResponseData<?> responseData = studentService.getStudentGroupSorted(page, size);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 401).body(responseData);
    }

//    @CheckPermission("GET_STUDENT")
//    @GetMapping("/get-student-group-filter/{groupId}")
//    public ResponseEntity<ResponseData<?>> getGroupFilter(@PathVariable UUID groupId) {
//        ResponseData<?> filter = studentService.getFilter(groupId);
//        return ResponseEntity.status(filter.isSuccess() ? 200 : 409).body(filter);
//    }

    @CheckPermission("EDIT_STUDENT")
    @PatchMapping("/update-student/{studentId}")
    public ResponseEntity<ResponseData<?>> updateStudent(@PathVariable UUID studentId, @RequestBody StudentUpdateDto updateDto) {
        ResponseData<?> responseData = studentService.updateStudent(studentId, updateDto);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 409).body(responseData);
    }

    @CheckPermission("EDIT_STUDENT")
    @PutMapping("/edit-user-transfer-intern/{userId}")
    public ResponseEntity<ResponseData<?>> userTransferToIntern(@PathVariable UUID userId) {
        ResponseData<?> responseData = studentService.userTransfer(userId);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 401).body(responseData);
    }

    @CheckPermission("GET_STUDENT")
    @GetMapping("/get-user/{userId}")
    public ResponseEntity<ResponseData<?>> getUser(@PathVariable UUID userId) {
        ResponseData<?> responseData = studentService.getUserById(userId);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 401).body(responseData);
    }

    @CheckPermission("GET_STUDENT")
    @GetMapping("/get-all-student-table")
    public ResponseEntity<?> getAllStudentTable(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "10") int size) {
        ResponseData<?> responseData = studentService.getAllStudent(page, size);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 409).body(responseData);
    }

    // userga tegishli projectlar
    @CheckPermission("GET_STUDENT")
    @GetMapping("/get-all-student-projects/{userId}")
    public ResponseEntity<?> getAllUsersProject(@PathVariable UUID userId) {
        ResponseData<?> responseData = studentService.getByAllStudentProject(userId);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 409).body(responseData);
    }

    @CheckPermission("GET_STUDENT")
    @GetMapping("/get-student-class-schedule/{studentId}")
    public ResponseEntity<?> getStudentSchedule(@PathVariable UUID studentId) {
        ResponseData<?> responseData = studentService.getStudentClassSchedule(studentId);
        return ResponseEntity.status(responseData.isSuccess() ? 200 : 409).body(responseData);
    }

}
