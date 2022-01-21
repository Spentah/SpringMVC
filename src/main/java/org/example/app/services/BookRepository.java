package org.example.app.services;

import org.apache.log4j.Logger;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BookRepository implements ProjectRepository<Book> {

    private Logger log = Logger.getLogger(BookRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
//    private final List<Book> repo = new ArrayList<>();

    @Override
    public List<Book> retrieveAll() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNum) -> {
            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setSize(rs.getInt("size"));
            return book;
        });
        return new ArrayList<>(books);
    }

    @Override
    public void store(Book book) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("author",book.getAuthor());
        parameterSource.addValue("title",book.getTitle());
        parameterSource.addValue("size",book.getSize());
        book.setId(book.hashCode());
//        if (!(book.getAuthor().equals("") && book.getTitle().equals("") && book.getSize() == null)) {
//            repo.add(book);
            jdbcTemplate.update("INSERT INTO books(author,title,size) VALUES(:author, :title, :size)", parameterSource);
            log.info("store new book: " + book);
//        } else {
            log.info("cannot store book: " + book);
//        }
    }

    @Override
    public boolean removeItemById(Integer bookId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id",bookId);
        for (Book book : retrieveAll()) {
            if (book.getId().equals(bookId)) {
                jdbcTemplate.update("DELETE FROM books WHERE id = :id", parameterSource);
                log.info("remove completed: " + book);
                return true;//repo.remove(book);
            }
        }
        return false;
    }

    public void removeByRegex(String regex) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("regex",regex);
        retrieveAll().forEach(book -> {
            if (book.getTitle().matches(regex) || book.getSize().toString().matches(regex) || book.getAuthor().matches(regex)) {
//                repo.remove(book);
                jdbcTemplate.update("DELETE FROM books WHERE title = :regex OR size = :regex OR author = :regex", parameterSource);
                log.info("book delete by regex: " + book);
            }
        });
    }
}
