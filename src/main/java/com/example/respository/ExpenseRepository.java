package com.example.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense,Long>{

}
