package com.example.bookcrudoperations.repository;

import com.example.bookcrudoperations.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class BookRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Book> findAllBooksSortedByTitleDesc() {
        String sql = "SELECT * FROM book ORDER BY title DESC";
        return jdbcTemplate.query(sql, this::mapRowToBook);
    }

    public void addBook(Book book) {
        String sql = "INSERT INTO book (id, title, author, description) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, book.getId(), book.getTitle(), book.getAuthor(), book.getDescription());
    }

    public List<Book> findBooksGroupedByAuthor() {
        String sql = """
                SELECT author, STRING_AGG(title, ', ' ORDER BY title) AS title
                FROM book
                GROUP BY author;
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                {
                    Book book = new Book();
                    book.setAuthor(rs.getString("author"));
                    book.setTitle(rs.getString("title"));
                    return book;
                }
        );
    }

    public List<Map<String, Object>> findAuthorsByCharacterFrequency(char character) {
        String sql = "SELECT author, LOWER(title) as title FROM book";
        List<Map<String, Object>> books = jdbcTemplate.queryForList(sql);

        return books.stream()
                .collect(Collectors.groupingBy(
                        b -> b.get("author"),
                        Collectors.summingInt(b -> countCharacterOccurrences((String) b.get("title"), character))
                ))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 0)
                .sorted((o1, o2) -> o2.getValue() - o1.getValue())
                .limit(10)
                .map(entry -> Map.of("author", entry.getKey(), "count", entry.getValue()))
                .toList();
    }

    private int countCharacterOccurrences(String text, char character) {
        long count = text.chars()
                .filter(ch -> Character.toLowerCase(ch) == Character.toLowerCase(character))
                .count();
        return (int) count;
    }

    private Book mapRowToBook(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getLong("id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setDescription(rs.getString("description"));
        return book;
    }
}
