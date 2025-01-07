package com.example.library;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/books")  // Ścieżka bazowa endpointu
@Produces(MediaType.APPLICATION_JSON)  // Zwracane dane w formacie JSON
@Consumes(MediaType.APPLICATION_JSON)  // Oczekiwane dane w formacie JSON
public class BooksResource {

    @Inject
    private BookService bookService;  // Wstrzyknięcie serwisu obsługującego logikę biznesową

    @GET
    @Path("/hello")
    public Response sayHelloFromBooks() {
        return Response.ok("Hello from BooksResource!").build();
    }

    @GET
    public Response getAllBooks() {
        System.out.println("GET /api/books called");
        if (bookService == null) {
            System.out.println("BookService is null");
        }
        List<Book> books = bookService.getAllBooks();
        return Response.ok(books).build();
    }

    @GET
    @Path("/{id}")  // Endpoint dla pobrania książki po ID
    public Response getBookById(@PathParam("id") int id) {
        Book book = bookService.getBookById(id);  // Pobranie książki o danym ID
        if (book != null) {
            return Response.ok(book).build();  // Zwrócenie książki (HTTP 200)
        } else {
            return Response.status(Response.Status.NOT_FOUND)  // Książka nie znaleziona (HTTP 404)
                    .entity("Book not found for ID: " + id)
                    .build();
        }
    }

    @POST
    public Response addBook(Book book) {
        Book createdBook = bookService.addBook(book);  // Dodanie nowej książki
        return Response.status(Response.Status.CREATED).entity(createdBook).build();  // Zwrócenie HTTP 201
    }

    @PUT
    @Path("/{id}")  // Endpoint dla aktualizacji książki
    public Response updateBook(@PathParam("id") int id, Book book) {
        Book updatedBook = bookService.updateBook(id, book);  // Aktualizacja książki
        if (updatedBook != null) {
            return Response.ok(updatedBook).build();  // Zwrócenie zaktualizowanej książki (HTTP 200)
        } else {
            return Response.status(Response.Status.NOT_FOUND)  // Książka nie znaleziona (HTTP 404)
                    .entity("Book not found for ID: " + id)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")  // Endpoint dla usunięcia książki
    public Response deleteBook(@PathParam("id") int id) {
        boolean deleted = bookService.deleteBook(id);  // Usunięcie książki
        if (deleted) {
            return Response.noContent().build();  // Zwrócenie HTTP 204 bez treści
        } else {
            return Response.status(Response.Status.NOT_FOUND)  // Książka nie znaleziona (HTTP 404)
                    .entity("Book not found for ID: " + id)
                    .build();
        }
    }
}