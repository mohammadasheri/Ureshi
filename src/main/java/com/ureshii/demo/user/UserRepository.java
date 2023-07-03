package com.ureshii.demo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UreshiiUser, Long> {
    Optional<UreshiiUser> findByUsername(String username);
}
