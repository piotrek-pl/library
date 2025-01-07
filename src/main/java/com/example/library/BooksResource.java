package com.example.library;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BooksResource {

    @Inject
    private BookService bookService;

    @GET
    public Response getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return Response.ok(books).build();
    }

    @GET
    @Path("/{id}")
    public Response getBookById(@PathParam("id") int id) {
        Book book = bookService.getBookById(id);
        if (book != null) {
            return Response.ok(book).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Book not found for ID: " + id)
                    .build();
        }
    }

    @POST
    public Response addBook(Book book) {
        Book createdBook = bookService.addBook(book);
        return Response.status(Response.Status.CREATED).entity(createdBook).build();
    }

    @PUT
    @Path("/{id}")
    public Response updateBook(@PathParam("id") int id, Book book) {
        Book updatedBook = bookService.updateBook(id, book);
        if (updatedBook != null) {
            return Response.ok(updatedBook).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Book not found for ID: " + id)
                    .build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteBook(@PathParam("id") int id) {
        boolean deleted = bookService.deleteBook(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Book not found for ID: " + id)
                    .build();
        }
    }
}
