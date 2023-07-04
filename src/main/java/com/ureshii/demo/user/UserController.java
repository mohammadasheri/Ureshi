package com.ureshii.demo.user;

import com.ureshii.demo.authentication.JwtUtils;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping(path = "/user")
public record UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {

    @PostMapping("/create")
    ResponseEntity<UserResponseDTO> createUser(@RequestBody @NotNull CreateUserRequestDTO dto) {
        UreshiiUser user = userService.createUserWithRole(dto);
        return new ResponseEntity<>(convertUser(user), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponseDTO> login(@RequestBody @NotNull LoginRequestDTO dto) {
        log.info("UserController-> login user");
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(dto.username(), dto.password()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        return new ResponseEntity<>(new LoginResponseDTO(dto.username(), "Bearer", jwt), HttpStatus.OK);
    }

    @PostMapping("/disable")
    void disableUser(@RequestBody @NotNull CreateUserRequestDTO dto) {
        userService.disableEnableUser(dto, false);
    }

    @PostMapping("/enable")
    void enableUser(@RequestBody @NotNull CreateUserRequestDTO dto) {
        userService.disableEnableUser(dto, true);
    }

    private UserResponseDTO convertUser(UreshiiUser user) {
        return new UserResponseDTO(user.getId(), user.getUsername());
    }
}
