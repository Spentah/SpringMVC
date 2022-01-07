package org.example.web.controllers;

import org.apache.log4j.Logger;
import org.example.app.services.BookService;
import org.example.web.dto.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping(value = "books")
public class BooksShelfController {

    private Logger log = Logger.getLogger(BooksShelfController.class);
    private BookService bookService;

    @Autowired
    public BooksShelfController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping(value = "shelf")
    public String books(Model model) {
        log.info("got books shelf");
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", bookService.getAllBooks());
        return "book_shelf";
    }

    @PostMapping(value = "save")
    public String saveBook(Book book) {
        bookService.saveBook(book);
        log.info("current repository size " + bookService.getAllBooks().size());
        return "redirect:/books/shelf";
    }

    @PostMapping(value = "remove")
    public String removeBook(@RequestParam(value = "bookIdToRemove") Integer bookIdToRemove) {
        if (!bookService.removeBookById(bookIdToRemove)) {
            log.info("cannot find book with id: " + bookIdToRemove);
        }
        return "redirect:/books/shelf";
    }

    @PostMapping(value = "removeByRegex")
    public String removeBookByRegex(@RequestParam(value = "queryRegex") String queryRegex) {
        bookService.removeBookByRegex(queryRegex);
        return "redirect:/books/shelf";
    }
}
