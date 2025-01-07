package com.example.library;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class BookRepository {

    private List<Book> books = new ArrayList<>();

    public List<Book> findAll() {
        return books;
    }

    public Book findById(int id) {
        return books.stream()
                .filter(book -> book.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Book save(Book book) {
        books.add(book);
        return book;
    }

    public Book update(int id, Book updatedBook) {
        Book existingBook = findById(id);
        if (existingBook != null) {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            return existingBook;
        }
        return null;
    }

    public boolean delete(int id) {
        return books.removeIf(book -> book.getId() == id);
    }
}
