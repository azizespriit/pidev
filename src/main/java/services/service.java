package services;

import java.util.List;

public interface  service<T> {
    void create(T obj) throws Exception;

    void update(T obj) throws Exception;

    void delete(T obj) throws Exception;

    List<T> getAll() throws Exception;

}
