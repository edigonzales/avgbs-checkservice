package ch.so.agi.avgbs.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.so.agi.avgbs.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
