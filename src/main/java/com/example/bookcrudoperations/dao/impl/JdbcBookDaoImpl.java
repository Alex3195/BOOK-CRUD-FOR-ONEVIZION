package com.example.bookcrudoperations.dao.impl;

import com.example.bookcrudoperations.dao.BookDao;
import com.example.bookcrudoperations.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class JdbcBookDaoImpl implements BookDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Book> getAllBooksSortedByTitle() {
        return jdbcTemplate.query("SELECT * FROM book ORDER BY title DESC",
                BeanPropertyRowMapper.newInstance(Book.class));
    }

    @Override
    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO book (id, title, author, description) VALUES (?, ?, ?, ?)",
                book.getId(), book.getTitle(), book.getAuthor(), book.getDescription());
    }

    @Override
    public List<Book> getAllBooksGroupedByAuthor() {
        return jdbcTemplate.query("SELECT * FROM book",
                BeanPropertyRowMapper.newInstance(Book.class));
    }

    @Override
    public List<Map<String, Object>> getAuthorsWithCharacterCount(char character) {
        return jdbcTemplate.queryForList("SELECT author, title FROM book");
    }

}

