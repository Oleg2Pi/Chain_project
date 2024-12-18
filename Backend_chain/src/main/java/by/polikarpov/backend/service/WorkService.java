package by.polikarpov.backend.service;

import by.polikarpov.backend.dto.WorkPageDto;
import by.polikarpov.backend.entity.Work;
import by.polikarpov.backend.mapper.ChosenWorkMapper;
import by.polikarpov.backend.repository.ExecutorRepository;
import by.polikarpov.backend.repository.WorkRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class WorkService implements CommonService<Work, Integer>{

    private final WorkRepository repository;
    private final ExecutorRepository executorRepository;
    private final ChosenWorkMapper mapper;
    private final String UPLOAD_DIR = "C:\\Users\\User\\Desktop\\files";

    @Autowired
    public WorkService(WorkRepository repository, ChosenWorkMapper mapper,
                       ExecutorRepository executorRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.executorRepository = executorRepository;
    }

    @Override
    public Work save(Work work) {
        return null;
    }

    @Override
    public Work update(Integer id, Work work) {
        return null;
    }

    @Override
    public Work findById(Integer id) {
        return null;
    }

    @Override
    public List<Work> findAll() {
        return List.of();
    }

    @Override
    public boolean deleteById(Integer id) {
        return false;
    }

    public WorkPageDto findWorkAndWorksForPage(int id) {
        Work work = findWorkById(id);
        return mapper.toDto(work);
    }

    private Work findWorkById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "Work with id: " + id + " not found"
        ));
    }

    public List<Work> findAllByExecutorId(Integer id) {
        return repository.findAllByExecutorId(id);
    }

    public Work saveWork(Integer executorId, String name,
                         Long dateAdded, String description, String type){
        int index = 0;

        if (repository.findIndexLastWork().isPresent()) {
                index = repository.findIndexLastWork().get().getId();
        }

        index++;

        Timestamp dateAddedNew = new Timestamp(dateAdded);

        Work work = Work.builder()
                .executor(executorRepository.findById(executorId).orElseThrow(EntityNotFoundException::new))
                .name(name)
                .dateAdded(dateAddedNew)
                .description(description)
                .type(type)
                .build();

        if (work.getType().startsWith("image/")) {
            work.setFile(
                    UPLOAD_DIR + "\\images_of_work\\" + index + "." +
                    work.getType().substring(
                            work.getType().lastIndexOf('/') + 1
                    )
            );
        }

        if (work.getType().startsWith("video/")) {
            work.setFile(
                    UPLOAD_DIR + "\\videos_of_work\\" + index + "." +
                    work.getType().substring(
                            work.getType().lastIndexOf('/') + 1
                    )
            );
        }

        return repository.save(work);
    }
}
