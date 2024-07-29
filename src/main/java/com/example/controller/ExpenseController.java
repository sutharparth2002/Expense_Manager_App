package com.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Expense;
import com.example.entity.User;
import com.example.service.ExpenseService;

import jakarta.websocket.server.PathParam;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.apache.bcel.classfile.Module.Uses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/expense")
public class ExpenseController {


    @Autowired
    ExpenseService expenseService;

    // add expense by exact amount mentioned from the client side
    @PostMapping("/addexpense/exact")
    public String addExpenseExact(@RequestParam("des") String des,@RequestParam("amount") 
    Double amount ,@RequestBody Map<Long,Double> exact) throws Exception {
       
        expenseService.addExpense(des,amount,exact);
        return null;
    }
    // add expense on percentage basis which is given in requestn body
    @PostMapping("/addexpense/percentage")
    public String addExpenseByPercentage(@RequestParam("des") String des,@RequestParam("amount") 
    Double amount ,@RequestBody Map<Long,Double> percentages) throws Exception {
        expenseService.addExpenseByPercentage(des,amount,percentages);
        return "Percentage Wise Expense Added";
    }
    // add expense equaly to all mentioned users
    @PostMapping("/addexpense/equal")
    public String addExpenseByEqual(@RequestParam("des") String des,@RequestParam("amount") 
    Double amount ,@RequestBody List<Long> lst) throws Exception {
        expenseService.addExpenseByEqual(des,amount,lst);
        
        return "Equal Expense Added";
    }

    @GetMapping("/all")
    public void postMethodName() {
        expenseService.getAllExpense();
    }
    
    @GetMapping("/{id}")
    public void getExpenseOfUser(@PathVariable Long id) {
        expenseService.getExpenseById(id);
    }
    
    // for downloading the balance_sheet
    @GetMapping("/downloadAll")
     public ResponseEntity<byte[]> downloadBalanceSheetTxt() {
        try {
            String txtContent = expenseService.generateBalanceSheetTxt();
            byte[] txtBytes = txtContent.getBytes();

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=balance_sheet.txt");
            headers.add(HttpHeaders.CONTENT_TYPE, "text/plain");

            return new ResponseEntity<>(txtBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
