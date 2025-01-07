package com.example.library;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class) // Używamy Mockito Extension
class BookServiceTest {

    @InjectMocks
    BookService bookService; // BookService będzie mieć wstrzyknięte mocki

    @Mock
    BookRepository bookRepository; // Mockujemy BookRepository

    @BeforeEach
    void setUp() {
        // Opcjonalnie: Możesz skonfigurować zachowanie mocka tutaj lub w poszczególnych testach
    }

    @Test
    void testAddAndGetBook() {
        Book book = new Book(1, "Effective Java", "Joshua Bloch");

        // Definiujemy zachowanie mocka
        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.findById(1)).thenReturn(book);

        // Wywołujemy metody serwisu
        bookService.addBook(book);
        Book retrievedBook = bookService.getBookById(1);

        // Weryfikujemy wyniki
        assertNotNull(retrievedBook);
        assertEquals("Effective Java", retrievedBook.getTitle());
        assertEquals("Joshua Bloch", retrievedBook.getAuthor());

        // Weryfikujemy interakcje z mockiem
        verify(bookRepository, times(1)).save(book);
        verify(bookRepository, times(1)).findById(1);
    }

    @Test
    void testUpdateBook() {
        Book book = new Book(2, "Clean Code", "Robert C. Martin");
        Book updatedBook = new Book(2, "Clean Code: A Handbook of Agile Software Craftsmanship", "Robert C. Martin");

        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.update(2, updatedBook)).thenReturn(updatedBook);
        when(bookRepository.findById(2)).thenReturn(updatedBook);

        bookService.addBook(book);
        bookService.updateBook(2, updatedBook);
        Book retrievedBook = bookService.getBookById(2);

        assertNotNull(retrievedBook);
        assertEquals("Clean Code: A Handbook of Agile Software Craftsmanship", retrievedBook.getTitle());

        verify(bookRepository, times(1)).save(book);
        verify(bookRepository, times(1)).update(2, updatedBook);
        verify(bookRepository, times(1)).findById(2);
    }

    @Test
    void testDeleteBook() {
        Book book = new Book(3, "The Pragmatic Programmer", "Andrew Hunt");

        when(bookRepository.save(book)).thenReturn(book);
        when(bookRepository.delete(3)).thenReturn(true);
        when(bookRepository.findById(3)).thenReturn(null);

        bookService.addBook(book);
        boolean deleted = bookService.deleteBook(3);
        Book retrievedBook = bookService.getBookById(3);

        assertTrue(deleted);
        assertNull(retrievedBook);

        verify(bookRepository, times(1)).save(book);
        verify(bookRepository, times(1)).delete(3);
        verify(bookRepository, times(1)).findById(3);
    }

    @Test
    void testGetAllBooks() {
        Book book1 = new Book(4, "Design Patterns", "Erich Gamma");
        Book book2 = new Book(5, "Refactoring", "Martin Fowler");

        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        bookService.addBook(book1);
        bookService.addBook(book2);
        List<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size());
        assertTrue(books.contains(book1));
        assertTrue(books.contains(book2));

        verify(bookRepository, times(2)).save(any(Book.class));
        verify(bookRepository, times(1)).findAll();
    }
}
