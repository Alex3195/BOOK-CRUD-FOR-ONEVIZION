package com.example.bookcrudoperations.controller;

import com.example.bookcrudoperations.model.Book;
import com.example.bookcrudoperations.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBooksSortedByTitleDesc() {
        return bookRepository.findAllBooksSortedByTitleDesc();
    }

    @PostMapping
    public ResponseEntity<Void> addBook(@RequestBody Book book) {
        bookRepository.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/grouped-by-author")
    public List<Book> getBooksGroupedByAuthor() {
        return bookRepository.findBooksGroupedByAuthor();
    }

    @GetMapping("/top-authors-by-character")
    public List<Map<String, Object>> getAuthorsByCharacterFrequency(@RequestParam char character) {
        return bookRepository.findAuthorsByCharacterFrequency(character);
    }
}
