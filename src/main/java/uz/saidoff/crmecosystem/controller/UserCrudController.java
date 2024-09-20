package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.UserCreateDto;
import uz.saidoff.crmecosystem.payload.UserDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.UserCrudService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user-crud")
public class UserCrudController {

    private final UserCrudService userCrudService;

    @PostMapping("create-user")
    public ResponseData<UserDto> createUser(UserCreateDto userDto) {
        return this.userCrudService.create(userDto);
    }

    @PutMapping("user-update/{userId}")
    public ResponseData<UserDto> updateUser(@PathVariable UUID userId, UserDto userDto) {
        return this.userCrudService.update(userId,userDto);
    }

    @GetMapping("/{userId}")
    public ResponseData<UserDto> getUser(@PathVariable UUID userId) {
        return this.userCrudService.getUser(userId);
    }

    @GetMapping("/get-all-users")
    public ResponseData<?> getUsers(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return this.userCrudService.getAllUser(page,size);
    }




}
