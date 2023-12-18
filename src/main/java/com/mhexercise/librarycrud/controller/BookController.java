package com.mhexercise.librarycrud.controller;

import com.mhexercise.librarycrud.entity.Book;
import com.mhexercise.librarycrud.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * Controller handling REST api calls related to books.
 */
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Retrieves all books.
     *
     * @return List of all books.
     */
    @GetMapping(produces = "application/json;charset=UTF-8")
    public List<Book> getBooks() {
        return bookService.findAll();
    }

    /**
     * Retrieves a specific book by ID.
     *
     * @param id ID of the book to retrieve.
     * @return ResponseEntity containing the requested book.
     */
    @GetMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity getBook(@PathVariable Long id) {
        Book book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    /**
     * Retrieves all loaned books.
     *
     * @return List of loaned books.
     */
    @GetMapping(path = "/loaned-books", produces = "application/json;charset=UTF-8")
    public List<Book> getLoanedBooks() {
        return bookService.findLoanedBooks();
    }

    /**
     * Retrieves all available books.
     *
     * @return List of available books.
     */
    @GetMapping(path = "/available-books", produces = "application/json;charset=UTF-8")
    public List<Book> getAvailableBooks() {
        return bookService.findAvailableBooks();
    }

    /**
     * Creates a new book.
     *
     * @param book The book object to be created.
     * @return ResponseEntity with the newly created book.
     * @throws URISyntaxException if there is an issue with the URI syntax.
     */
    @PostMapping(produces = "application/json;charset=UTF-8")
    public ResponseEntity createBook(@RequestBody Book book) throws URISyntaxException {
        Book newBook = bookService.save(book);
        return ResponseEntity.created(new URI("/books/" + newBook.getId())).body(newBook);
    }

    /**
     * Loans a book.
     *
     * @param id   ID of the book to be loaned.
     * @param book Book object containing loan information.
     * @return ResponseEntity with the newly loaned book.
     */
    @PutMapping(path = "/loan-book/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity loanBook(@PathVariable Long id, @RequestBody Book book) {
        Book newlyLoanedBook = bookService.loanBook(id, book.getLoan());
        return ResponseEntity.ok(newlyLoanedBook);
    }

    /**
     * Returns a loaned book.
     *
     * @param id ID of the book to be returned.
     * @return ResponseEntity with the returned book.
     */
    @PutMapping(path = "/return-book/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity returnBook(@PathVariable Long id) {
        Book returnedBook = bookService.returnBook(id);
        return ResponseEntity.ok(returnedBook);
    }

    /**
     * Edits a book.
     *
     * @param id   ID of the book to be edited.
     * @param book Book object with edited information.
     * @return ResponseEntity with the edited book.
     */
    @PutMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity editBook(@PathVariable Long id, @RequestBody Book book) {
        Book updatedBook = bookService.editBook(id, book);
        return ResponseEntity.ok(updatedBook);
    }

    /**
     * Deletes a book.
     *
     * @param id ID of the book to be deleted.
     * @return ResponseEntity indicating the deletion.
     */
    @DeleteMapping(path = "/{id}", produces = "application/json;charset=UTF-8")
    public ResponseEntity deleteBook(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
