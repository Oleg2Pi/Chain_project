package by.polikarpov.backend.mapper;

import by.polikarpov.backend.dto.WorkPageDto;
import by.polikarpov.backend.entity.Work;
import by.polikarpov.backend.repository.WorkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChosenWorkMapper {

    private final WorkRepository repository;

    @Autowired
    public ChosenWorkMapper(WorkRepository repository) {
        this.repository = repository;
    }

    public WorkPageDto toDto(Work work) {
        if (work == null) {
            return null;
        }

        long chatId = work.getExecutor().getPerson().getChatId();
        String firstName = work.getExecutor().getPerson().getFirstName();
        String lastName = work.getExecutor().getPerson().getLastName();
        String personImage = null;
        String activityArea = null;
        String statusCategory = null;

        if (work.getExecutor().getResume() != null) {
            activityArea = work.getExecutor().getResume().getActivityArea().getType();
            statusCategory = work.getExecutor().getResume().getUserStatus().getCategory();
        }

        if (work.getExecutor().getPerson().getImage() != null) {
            personImage = work.getExecutor().getPerson().getImage().getFilePath();
        }

        List<Work> works = repository.findAllWorksWithoutOne(work.getId(), work.getExecutor().getId());

        return new WorkPageDto(
                chatId,
                firstName,
                lastName,
                personImage,
                activityArea,
                statusCategory,
                work.getId(),
                work.getFile(),
                work.getName(),
                work.getDescription(),
                works
        );
    }
}
