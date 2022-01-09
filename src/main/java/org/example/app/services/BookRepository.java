package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private Logger log = Logger.getLogger(BookRepository.class);
    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retrieveAll() {
        return new ArrayList<>(repo);
    }

    @Override
    public void store(Book book) {
        book.setId(String.valueOf(book.hashCode()));
        if (!(book.getAuthor().equals("") && book.getTitle().equals("") && book.getSize() == null)) {
            repo.add(book);
            log.info("store new book: " + book);
        } else {
            log.info("cannot store book: " + book);
        }
    }

    @Override
    public boolean removeItemById(String bookId) {
        for (Book book : retrieveAll()) {
            if (book.getId().equals(bookId)) {
                log.info("remove completed: " + book);
                return repo.remove(book);
            }
        }
        return false;
    }

    public void removeByRegex(String regex) {
        retrieveAll().forEach(book -> {
            if (book.getTitle().matches(regex) || book.getSize().toString().matches(regex) || book.getAuthor().matches(regex)) {
                repo.remove(book);
                log.info("book delete by regex: " + book);
            }
        });
    }
}
