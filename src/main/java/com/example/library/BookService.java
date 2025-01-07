package com.example.library;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import java.util.List;

@RequestScoped
public class BookService {

    @Inject
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(int id) {
        return bookRepository.findById(id);
    }

    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(int id, Book book) {
        return bookRepository.update(id, book);
    }

    public boolean deleteBook(int id) {
        return bookRepository.delete(id);
    }
}
