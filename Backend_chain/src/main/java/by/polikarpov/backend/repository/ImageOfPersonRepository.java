package by.polikarpov.backend.repository;

import by.polikarpov.backend.entity.ImageOfPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ImageOfPersonRepository extends JpaRepository<ImageOfPerson, Integer> {
    @Query("SELECT i FROM ImageOfPerson i WHERE i.person.chatId = :chatId")
    ImageOfPerson findByPersonChatId(Long chatId);

    @Query("SELECT i FROM ImageOfPerson  i ORDER BY i.id DESC LIMIT 1")
    Optional<ImageOfPerson> findImageOfPersonByLastId();
}
