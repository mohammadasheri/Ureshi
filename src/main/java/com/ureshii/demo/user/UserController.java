package com.ureshii.demo.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.constraints.NotNull;


@Slf4j
@RestController
@RequestMapping(path = "/user")
public record UserController(UserService userService) {

    @PostMapping("/create")
    void createUser(@RequestBody @NotNull UserRequestDTO dto) {
        userService.createUserWithRole(dto);
    }

    @PostMapping("/disable")
    void disableUser(@RequestBody @NotNull UserRequestDTO dto) {
        userService.disableEnableUser(dto, false);
    }

    @PostMapping("/enable")
    void enableUser(@RequestBody @NotNull UserRequestDTO dto) {
        userService.disableEnableUser(dto, true);
    }
}
