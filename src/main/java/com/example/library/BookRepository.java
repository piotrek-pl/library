package com.example.library;

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class BookRepository {

    private final List<Book> books = new ArrayList<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

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
        book.setId(nextId.getAndIncrement());
        books.add(book);
        return book;
    }

    public Book update(int id, Book updatedBook) {
        Book existingBook = findById(id);
        if (existingBook != null) {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setIsbn(updatedBook.getIsbn());
            return existingBook;
        }
        return null;
    }

    public boolean delete(int id) {
        return books.removeIf(book -> book.getId() == id);
    }
}