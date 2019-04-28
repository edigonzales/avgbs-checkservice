package ch.so.agi.avgbs.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.so.agi.avgbs.models.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

}
