package com.mhexercise.librarycrud.service;

import com.mhexercise.librarycrud.entity.Book;
import com.mhexercise.librarycrud.entity.Loan;
import com.mhexercise.librarycrud.exception.BookException;
import com.mhexercise.librarycrud.repository.BookRepository;
import com.mhexercise.librarycrud.repository.LoanRepository;
import com.mhexercise.librarycrud.repository.PersonRepository;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private final EntityManager entityManager;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;
    private final PersonRepository personRepository;

    @Autowired
    public BookServiceImpl(EntityManager entityManager, BookRepository bookRepository, LoanRepository loanRepository, PersonRepository personRepository) {
        this.entityManager = entityManager;
        this.loanRepository = loanRepository;
        this.bookRepository = bookRepository;
        this.personRepository = personRepository;
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> findLoanedBooks() {
        return findAll().stream().filter(book -> book.getLoan() != null).collect(Collectors.toList());
    }

    @Override
    public List<Book> findAvailableBooks() {
        return findAll().stream().filter(book -> book.getLoan() == null).collect(Collectors.toList());
    }

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        Book bookToBeDeleted = null;
        try {
            bookToBeDeleted = bookRepository.findById(id).orElseThrow(
                    () -> new BookException("Book to be deleted does not exist"));
        } catch (BookException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new BookException(e);
        }

        if (bookToBeDeleted.getLoan() != null) {
            loanRepository.deleteById(bookToBeDeleted.getLoan().getId());
        }
        bookRepository.deleteById(id);
    }

    @Override
    public Book returnBook(Long id) {
        Book bookToBeReturned = null;
        try {
            bookToBeReturned = bookRepository.findById(id).orElseThrow(
                    () -> new BookException("Book to be returned does not exist"));
        } catch (BookException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new BookException(e);
        }

        Long loanId = bookToBeReturned.getLoan().getId();
        bookToBeReturned.setLoan(null);
        loanRepository.deleteById(loanId);
        logger.debug("Book with id %s has been returned".formatted(id));
        return bookToBeReturned;
    }

    @Override
    public Book editBook(Long id, Book update) {
        Book bookToBeUpdated = null;
        try {
            bookToBeUpdated = bookRepository.findById(id).orElseThrow(
                    () -> new BookException("Book to be updated does not exist"));
        } catch (BookException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new BookException(e);
        }

        bookToBeUpdated.setAuthor(update.getAuthor());
        bookToBeUpdated.setName(update.getName());

        return bookRepository.save(bookToBeUpdated);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book loanBook(Long id, Loan loan) {
        Book bookToBeLoaned = null;
        try {
            bookToBeLoaned = bookRepository.findById(id).orElseThrow(
                    () -> new BookException("Book to be loaned does not exist"));
        } catch (BookException e) {
            logger.error(e.getLocalizedMessage(), e);
            throw new BookException(e);
        }

        // Search for person by firstName & lastName and assign if exists. In real world multiple
        // people with same name can exist, so different identifier would be used for matching.
        personRepository.findOne(Example.of(loan.getPerson())).ifPresent(loan::setPerson);

        loan.setBook(bookToBeLoaned);
        loanRepository.save(loan);
        entityManager.refresh(bookToBeLoaned);
        return bookToBeLoaned;
    }
}
