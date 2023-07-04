package com.ureshii.demo.user;


import com.ureshii.demo.role.Role;
import com.ureshii.demo.user.dto.CreateUserRequestDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService extends UserDetailsService {

    UreshiiUser createUserWithRole(CreateUserRequestDTO user);

    UreshiiUser createUser(String name, String password, Role role);

    boolean disableEnableUser(CreateUserRequestDTO user, boolean enabled);

    List<UreshiiUser> findAll();

    UreshiiUser findByUserName(String username);

    void saveOrUpdate(UreshiiUser user);
}