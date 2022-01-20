package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {

    List<T> retrieveAll();

    void store(T book);

    boolean removeItemById(Integer bookId);

    void removeByRegex(String regex);
}
