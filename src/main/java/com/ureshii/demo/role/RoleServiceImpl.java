package com.ureshii.demo.role;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findOrCreateRole(String roleName) {
        log.debug("Role Service-> find or create role");
        validateRole(roleName);
        Role role = findByName(roleName);
        if (role == null) {
            return createNewRole(roleName);
        } else return role;
    }

    private Role createNewRole(String role) {
        log.info("Role Service-> create new role");
        Role newRole = new Role();
        newRole.setName(role);
        saveOrUpdate(newRole);
        return newRole;
    }

    @Override
    public void validateRole(String role) {
        log.debug("Role Service-> validate role");
        if (ObjectUtils.isEmpty(role)) throw new ServiceException("Role is null!");
        if (role.equalsIgnoreCase("Admin"))
            throw new ServiceException("You can not edit or define an admin role!");
    }

    @Override
    public void saveOrUpdate(Role newRole) {
        roleRepository.save(newRole);
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}