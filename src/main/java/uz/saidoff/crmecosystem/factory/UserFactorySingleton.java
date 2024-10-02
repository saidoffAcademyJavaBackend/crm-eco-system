package uz.saidoff.crmecosystem.factory;


import uz.saidoff.crmecosystem.entity.Group;
import uz.saidoff.crmecosystem.entity.auth.Role;
import uz.saidoff.crmecosystem.entity.auth.User;
import uz.saidoff.crmecosystem.enums.Permissions;
import uz.saidoff.crmecosystem.payload.GroupCreateDto;
import uz.saidoff.crmecosystem.payload.UserCreateDto;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class UserFactorySingleton {


    private static UserFactorySingleton instance;

    private UserFactorySingleton() {
    }

    public static synchronized UserFactorySingleton getInstance() {
        if (instance == null) {
            instance = new UserFactorySingleton();
        }
        return instance;
    }

    public User createUser(String firstName, String lastName, String phoneNumber, String password, Role role, List<Permissions> permissionsSet) {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .password(password)
                .role(role)
                .permissions(permissionsSet)
                .enabled(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .build();
    }

    public User createUser(UserCreateDto userDto, Role role) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .phoneNumber(userDto.getPhoneNumber())
                .password(userDto.getPassword())
                .role(role)
                .permissions(userDto.getPermissions())
                .enabled(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .build();
    }
}
