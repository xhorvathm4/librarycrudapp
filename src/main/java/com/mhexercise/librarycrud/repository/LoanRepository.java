package com.mhexercise.librarycrud.repository;

import com.mhexercise.librarycrud.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan, Long> {
}
