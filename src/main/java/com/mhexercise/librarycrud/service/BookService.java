package com.mhexercise.librarycrud.service;

import com.mhexercise.librarycrud.entity.Book;
import com.mhexercise.librarycrud.entity.Loan;

import java.util.List;

public interface BookService {

    List<Book> findAll();

    List<Book> findLoanedBooks();

    List<Book> findAvailableBooks();

    Book findById(Long id);

    void deleteById(Long id);

    Book returnBook(Long id);

    Book editBook(Long id, Book update);

    Book save(Book book);

    Book loanBook(Long id, Loan loan);
}
