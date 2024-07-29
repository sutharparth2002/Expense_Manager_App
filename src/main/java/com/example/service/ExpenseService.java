package com.example.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.example.entity.Expense;

public interface ExpenseService {

    

    void addExpenseByPercentage(String des, Double amount, Map<Long, Double> percentages) throws Exception;

    void addExpenseByEqual(String des, Double amount, List<Long> lst) throws Exception;

    void getAllExpense();

    String generateBalanceSheetTxt() throws IOException;
    void getExpenseById(Long id);

    void addExpense(String des, Double amount, Map<Long, Double> exact) throws Exception;


}
