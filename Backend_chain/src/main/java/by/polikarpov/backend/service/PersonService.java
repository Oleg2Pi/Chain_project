package by.polikarpov.backend.service;

import by.polikarpov.backend.dto.PersonProfileDto;
import by.polikarpov.backend.dto.PersonsHomePageDto;
import by.polikarpov.backend.entity.Executor;
import by.polikarpov.backend.entity.Person;
import by.polikarpov.backend.mapper.PersonMapper;
import by.polikarpov.backend.repository.PersonRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService implements CommonService<Person, Long> {

    private final PersonRepository repository;
    private final PersonMapper mapper;

    @Autowired
    public PersonService (PersonRepository repository, PersonMapper mapper) {
        this.mapper = mapper;
        this.repository = repository;
    }

    // person created in telegram-bot
    @Override
    public Person save(Person entity) {
        return repository.save(entity);
    }

    @Override
    public Person update(Long chatId, Person entity) {
        findPersonByChatId(chatId);
        entity.setChatId(chatId);
        return repository.save(entity);
    }

    @Override
    public Person findById(Long chatId) {
        return findPersonByChatId(chatId);
    }

    @Override
    public List<Person> findAll() {
        return repository.findAll();
    }

    @Override
    public boolean deleteById(Long chatId) {
        findPersonByChatId(chatId);
        repository.deleteByChatId(chatId);
        return true;
    }

    private Person findPersonByChatId(Long chatId) {
        return repository.findByChatId(chatId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Person with chat_id: " + chatId + " not found"
                ));
    }

    public List<PersonsHomePageDto> findAllByHomePage() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public PersonProfileDto findProfileByChatId(Long chatId) {
        Person person = findPersonByChatId(chatId);
        return mapper.toDtoProfile(person);
    }


    public void createPerson(Long chatId, String firstName, String lastName, String usernameTG,
                             String phone, String work) {
        if (repository.findByChatId(chatId).isPresent()) {
                throw new EntityExistsException("Person with chat_id: " + chatId + " already exists");
            }

            Person person = Person.builder()
                    .chatId(chatId)
                    .firstName(firstName)
                    .lastName(lastName)
                    .usernameTG(usernameTG)
                    .phone(phone)
                    .build();

            if (work.equals("executor")) {
                Executor executor = Executor.builder()
                        .person(person)
                        .build();

                person.setExecutor(executor);
            }

            repository.save(person);
    }
}
