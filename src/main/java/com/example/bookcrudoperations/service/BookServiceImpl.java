package com.example.bookcrudoperations.service;

import com.example.bookcrudoperations.dao.BookDao;
import com.example.bookcrudoperations.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    BookDao bookDao;

    @Override
    public List<Book> getAllBooksSortedByTitle() {
        return bookDao.getAllBooksSortedByTitle();
    }

    @Override
    public void addBook(Book book) {
        bookDao.addBook(book);
    }

    @Override
    public Map<String, List<Book>> getAllBooksGroupedByAuthor() {
        return bookDao.getAllBooksGroupedByAuthor().stream()
                .collect(Collectors.groupingBy(Book::getAuthor));
    }

    @Override
    public List<Map<String, ? extends Serializable>> getAuthorsWithCharacterCount(char character) {
        List<Map<String, Object>> books = bookDao.getAuthorsWithCharacterCount(character);

        Map<String, Long> authorCharacterCountMap = books.stream()
                .filter(book -> book.get("title") != null)
                .collect(Collectors.groupingBy(
                        book -> (String) book.get("author"),
                        Collectors.summingLong(book -> countCharacter((String) book.get("title"), character))
                ));

        return authorCharacterCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() > 0)
                .map(entry -> {
                    String author = entry.getKey();
                    Long characterCount = entry.getValue();
                    return Map.of("author", author, "character_count", characterCount);
                })
                .collect(Collectors.toList());
    }

    private long countCharacter(String title, char character) {
        return title.chars().filter(c -> Character.toLowerCase(c) == Character.toLowerCase(character)).count();
    }
}
