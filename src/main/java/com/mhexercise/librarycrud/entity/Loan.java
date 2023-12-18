package com.mhexercise.librarycrud.entity;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="loan")
@JsonIdentityInfo(
        scope = Loan.class,
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH, CascadeType.PERSIST})
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    Book book;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name="person_id")
    Person person;

    @Column(name = "borrowed_from", columnDefinition = "DATE")
    private LocalDate borrowedFrom;

    public Loan() {
    }

    public Loan(Book book, Person person, LocalDate borrowedFrom) {
        this.book = book;
        this.person = person;
        this.borrowedFrom = borrowedFrom;
    }

    @PreRemove
    private void removeAssociations() {
        this.setBook(null);
        this.setPerson(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public LocalDate getBorrowedFrom() {
        return borrowedFrom;
    }

    public void setBorrowedFrom(LocalDate borrowedFrom) {
        this.borrowedFrom = borrowedFrom;
    }

}
