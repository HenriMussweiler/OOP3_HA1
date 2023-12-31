package core.service;

import java.util.List;

public interface IAusleihvorgangService<T> {
    void save(T entity);
    T update(T entity);
    boolean delete(long entityId);
    T find(long entityId);
    List<T> findAll();
}
