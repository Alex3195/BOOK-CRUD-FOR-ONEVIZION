package com.example.bookcrudoperations.controller;

import com.example.bookcrudoperations.model.Book;
import com.example.bookcrudoperations.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/sorted")
    public ResponseEntity<List<Book>> getAllBooksSortedByTitle() {
        List<Book> books = bookService.getAllBooksSortedByTitle();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return new ResponseEntity<>("Book added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/grouped")
    public ResponseEntity<Map<String, List<Book>>> getAllBooksGroupedByAuthor() {
        Map<String, List<Book>> books = bookService.getAllBooksGroupedByAuthor();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/authors/{character}")
    public ResponseEntity<List<Map<String, ? extends Serializable>>> getAuthorsWithCharacterCount(@PathVariable char character) {
        List<Map<String, ? extends Serializable>> result = bookService.getAuthorsWithCharacterCount(character);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
