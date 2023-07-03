package com.ureshii.demo.user;

import com.ureshii.demo.role.Role;
import com.ureshii.demo.role.RoleEnum;
import com.ureshii.demo.role.RoleService;
import jakarta.transaction.Transactional;
import org.hibernate.service.spi.ServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, RoleService roleService, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @Override
    public UreshiiUser createUserWithRole(CreateUserRequestDTO dto) {
        Role role = roleService.findOrCreateRole(RoleEnum.User.name());
        Optional<UreshiiUser> user = userRepository.findByUsername(dto.username());
        return user.orElseGet(() -> createUser(dto.username(), dto.password(), role));
    }

    @Override
    public boolean disableEnableUser(CreateUserRequestDTO dto, boolean enabled) {
        Optional<UreshiiUser> userOptional = findByUsernameAndPassword(dto.username(), dto.password());
        if (userOptional.isPresent()) {
            UreshiiUser user = userOptional.get();
            user.setEnabled(enabled);
            userRepository.save(user);
            return true;
        } else
            throw new ServiceException("USER_NOT_FOUND");
    }

    @Override
    public UreshiiUser createUser(String name, String password, Role role) {
        UreshiiUser user = new UreshiiUser();
        user.setUsername(name);
        user.setPassword(encoder.encode(password));
        user.setEnabled(true);
        user.setRoles(Collections.singleton(role));
        return userRepository.save(user);
    }

    @Override
    public List<UreshiiUser> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UreshiiUser findByUserName(String username) {
        Optional<UreshiiUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return user.get();
    }

    @Override
    public void saveOrUpdate(UreshiiUser user) {
        userRepository.save(user);
    }

    private Optional<UreshiiUser> findByUsernameAndPassword(String username, String password) {
        Optional<UreshiiUser> byUsername = userRepository.findByUsername(username);
        if (byUsername.isPresent()) {
            if (encoder.matches(password, byUsername.get().getPassword())) {
                return byUsername;
            }
        }
        return Optional.empty();
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        Optional<UreshiiUser> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Could not find user");
        }
        return new UreshiiUserDetails(user.get());
    }
}