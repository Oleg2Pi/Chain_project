package by.polikarpov.backend.repository;

import by.polikarpov.backend.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Integer> {
    Optional<Person> findByChatId(Long chatId);
    void deleteByChatId(Long chatId);
}
