package ch.so.agi.avgbs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.so.agi.avgbs.models.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
