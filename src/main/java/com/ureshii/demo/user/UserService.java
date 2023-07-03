package com.ureshii.demo.user;


import com.ureshii.demo.role.Role;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.List;

public interface UserService extends UserDetailsService {

    void createUserWithRole(UserRequestDTO haraUser);

    void createUser(String name, String password, Role role);

    boolean disableEnableUser(UserRequestDTO haraUser, boolean enabled);

    List<UreshiiUser> findAll();

    UreshiiUser findByUserName(String username);

    void saveOrUpdate(UreshiiUser user);
}