package dev.batyrnosquare.auth_ustudy.repository;


import dev.batyrnosquare.auth_ustudy.data.Role;
import dev.batyrnosquare.auth_ustudy.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByEmailAndPassword(String email, String password);
    List<User> findAllByRole(Role role);
}
