package com.mhexercise.librarycrud.repository;

import com.mhexercise.librarycrud.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
