package org.example.app.services;

import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final ProjectRepository<Book> bookProjectRepository;

    @Autowired
    public BookService(BookRepository bookProjectRepository) {
        this.bookProjectRepository = bookProjectRepository;
    }

    public List<Book> getAllBooks() {
        return bookProjectRepository.retrieveAll();
    }

    public void saveBook(Book book) {
        bookProjectRepository.store(book);
    }

    public boolean removeBookById(Integer bookId) {
        return bookProjectRepository.removeItemById(bookId);
    }

    public void removeBookByRegex(String regex) {
        bookProjectRepository.removeByRegex(regex);
    }
}
