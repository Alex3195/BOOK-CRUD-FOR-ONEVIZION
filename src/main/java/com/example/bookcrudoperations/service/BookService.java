package com.example.bookcrudoperations.service;

import com.example.bookcrudoperations.model.Book;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BookService {

    List<Book> getAllBooksSortedByTitle();
    void addBook(Book book);
    Map<String, List<Book>> getAllBooksGroupedByAuthor();
    List<Map<String, ? extends Serializable>> getAuthorsWithCharacterCount(char character);
}
