package com.ureshii.demo.role;

import java.util.List;

public interface RoleService {
    Role findByName(String name);

    List<Role> findAll();

    void saveOrUpdate(Role newRole);

    Role findOrCreateRole(String roleName);

    void validateRole(String role);

}
