package com.example.bookcrudoperations.dao;

import com.example.bookcrudoperations.model.Book;

import java.util.List;
import java.util.Map;

public interface BookDao {
    List<Book> getAllBooksSortedByTitle();

    void addBook(Book book);

    List<Book> getAllBooksGroupedByAuthor();

    List<Map<String, Object>> getAuthorsWithCharacterCount(char character);
}
