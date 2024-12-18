package by.polikarpov.backend.service;

import java.util.List;

public interface CommonService<T, K> {
    T save(T entity);
    T update(K id, T entity);
    T findById(K id);
    List<T> findAll();
    boolean deleteById(K id);
}
