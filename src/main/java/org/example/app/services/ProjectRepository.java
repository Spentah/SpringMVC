package org.example.app.services;

import java.util.List;

public interface ProjectRepository<T> {

    List<T> retrieveAll();

    void store(T book);

    boolean removeItemById(String bookId);

    void removeByRegex(String regex);
}
