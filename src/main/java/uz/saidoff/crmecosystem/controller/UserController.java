package uz.saidoff.crmecosystem.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import uz.saidoff.crmecosystem.payload.UserCreateDto;
import uz.saidoff.crmecosystem.payload.UserDto;
import uz.saidoff.crmecosystem.response.ResponseData;
import uz.saidoff.crmecosystem.service.UserService;
import uz.saidoff.crmecosystem.valid.CheckPermission;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @CheckPermission("CREATE_USER")
    @PostMapping("create-user")
    public ResponseData<?> createUser(UserCreateDto userDto) {
        return this.userService.create(userDto);
    }

    @CheckPermission("EDIT_USER")
    @PutMapping("user-update/{userId}")
    public ResponseData<UserDto> updateUser(@PathVariable UUID userId, UserDto userDto) {
        return this.userService.update(userId,userDto);
    }

    @CheckPermission("GET_USER")
    @GetMapping("/{userId}")
    public ResponseData<UserDto> getUser(@PathVariable UUID userId) {
        return this.userService.getUser(userId);
    }

    @CheckPermission("GET_USER")
    @GetMapping("/get-all-users")
    public ResponseData<?> getUsers(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return this.userService.getAllUser(page,size);
    }




}
