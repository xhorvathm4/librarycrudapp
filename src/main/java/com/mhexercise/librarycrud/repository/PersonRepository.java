package com.mhexercise.librarycrud.repository;

import com.mhexercise.librarycrud.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
