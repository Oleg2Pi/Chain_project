package by.polikarpov.backend.repository;

import by.polikarpov.backend.entity.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface WorkRepository extends JpaRepository<Work, Integer> {
    @Query("SELECT w FROM Work w WHERE w.executor.id = :executorId ORDER BY " +
           "w.dateAdded DESC LIMIT 1")
    Work findLastByExecutorId(Integer executorId);

    @Query("SELECT w FROM Work w WHERE w.executor.id = :executorId ORDER BY " +
           "w.dateAdded DESC LIMIT 4")
    List<Work> findLastFourthWorksByExecutorId(Integer executorId);

    @Query("SELECT w FROM Work w WHERE w.executor.id = :executorId AND w.id <> :workId ORDER BY " +
           "w.dateAdded DESC LIMIT 8")
    List<Work> findAllWorksWithoutOne(Integer workId, Integer executorId);

    @Query("SELECT w FROM Work w WHERE w.executor.id = :id ORDER BY " +
           "w.dateAdded DESC")
    List<Work> findAllByExecutorId(Integer id);

    @Query("SELECT w.id FROM Work w ORDER BY w.id DESC LIMIT 1")
    Optional<Work> findIndexLastWork();
}
