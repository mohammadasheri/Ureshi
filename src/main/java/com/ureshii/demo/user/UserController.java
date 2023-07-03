package com.ureshii.demo.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    ResponseEntity<UserResponseDTO> createUser(@RequestBody @NotNull CreateUserRequestDTO dto) {
        UreshiiUser user = userService.createUserWithRole(dto);
        return new ResponseEntity<>(convertUser(user), HttpStatus.CREATED);
    }

    private UserResponseDTO convertUser(UreshiiUser user) {
        return new UserResponseDTO(user.getId(), user.getUsername());
    }

    @PostMapping("/disable")
    void disableUser(@RequestBody @NotNull CreateUserRequestDTO dto) {
        userService.disableEnableUser(dto, false);
    }

    @PostMapping("/enable")
    void enableUser(@RequestBody @NotNull CreateUserRequestDTO dto) {
        userService.disableEnableUser(dto, true);
    }
}
