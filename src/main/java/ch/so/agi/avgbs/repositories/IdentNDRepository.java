package ch.so.agi.avgbs.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.so.agi.avgbs.models.IdentND;

public interface IdentNDRepository extends JpaRepository<IdentND, Long> {
    Optional<IdentND> findByIdentnd(String identnd);
}
