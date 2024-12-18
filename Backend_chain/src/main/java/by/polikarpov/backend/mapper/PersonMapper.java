package by.polikarpov.backend.mapper;

import by.polikarpov.backend.dto.PersonProfileDto;
import by.polikarpov.backend.dto.PersonsHomePageDto;
import by.polikarpov.backend.entity.*;
import by.polikarpov.backend.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class PersonMapper {

    private final WorkRepository repository;

    @Autowired
    public PersonMapper(WorkRepository repository) {
        this.repository = repository;
    }

    /**
     * Преобразует сущность Person в DTO PersonsHomePageDto.
     *
     * @param person Исходный объект Person
     * @return DTO PersonsHomePageDto или null, если person равен null
     */
    public PersonsHomePageDto toDto(Person person) {
        if (person == null) {
            return null;
        }

        String imageFilePath = Optional.ofNullable(person.getImage())
                .map(ImageOfPerson::getFilePath)
                .orElse(null);

        if (person.getExecutor() != null && !person.getExecutor().getWorks().isEmpty()) {
            Work work = repository.findLastByExecutorId(person.getExecutor().getId());
            if (work != null) {
                return new PersonsHomePageDto(
                        person.getChatId(),
                        person.getFirstName(),
                        person.getLastName(),
                        imageFilePath,
                        work.getId(),
                        work.getFile(),
                        work.getType()
                );
            }
        }

        return new PersonsHomePageDto(
                person.getChatId(),
                person.getFirstName(),
                person.getLastName(),
                imageFilePath,
                -1,
                null,
                null
        );
    }

    /**
     * Преобразует сущность Person в DTO OtherPersonProfileDto.
     *
     * @param person Исходный объект Person
     * @return DTO OtherPersonProfileDto или null, если person равен null
     */
    public PersonProfileDto toDtoProfile(Person person) {
        if (person == null) {
            return null;
        }

        String personImage = Optional.ofNullable(person.getImage())
                .map(ImageOfPerson::getFilePath)
                .orElse(null);

        String activityArea = null;
        String statusCategory = null;
        List<Work> works = new ArrayList<>();
        int executorId = -1;

        if (person.getExecutor() != null) {
            if (person.getExecutor().getResume() != null) {
                activityArea = Optional.ofNullable(person.getExecutor().getResume().getActivityArea())
                        .map(ActivityArea::getType)
                        .orElse(null);

                statusCategory = Optional.ofNullable(person.getExecutor().getResume().getUserStatus())
                        .map(UserStatus::getCategory)
                        .orElse(null);
            }

            executorId = person.getExecutor().getId();
            works = repository.findLastFourthWorksByExecutorId(person.getExecutor().getId());
        }

        return new PersonProfileDto(
                person.getChatId(),
                person.getFirstName(),
                personImage,
                activityArea,
                statusCategory,
                works,
                executorId
        );
    }
}