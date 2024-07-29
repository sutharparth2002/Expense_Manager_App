package com.example.service;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Expense;
import com.example.entity.User;
import com.example.respository.ExpenseRepository;
import com.example.respository.UserRepository;

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    UserRepository userRepository;
   
    @Override
    public void addExpenseByPercentage(String des, Double amount,  Map<Long, Double> percentages) throws Exception {
        
        Expense expense;
        Map<User,Double> map=new HashMap<User,Double>();
        Double per=(double) 0;
        for(Map.Entry<Long,Double> e:percentages.entrySet()){
            per+=e.getValue();
            try {
                Optional<User> u=userRepository.findById(e.getKey());
                if(u.get()!=null)
                    map.put(u.get(),(amount*e.getValue())/100);               
            } catch (Exception exception) {
                throw error("no user found with id "+e.getKey());
            }
        }
        if(per!=100){
            throw error("Total Percentage is always 100");
        }
        expense=Expense.builder().amount(amount).desc(des).splits(map).build();
        expenseRepository.save(expense);
    }
    private Exception error(String string) {
        throw new UnsupportedOperationException(string);
    }
    @Override
    public void addExpenseByEqual(String des, Double amount,  List<Long> lst) throws Exception {
        Expense expense;
        Map<User,Double> map=new HashMap<User,Double>();
        
        Double individualAmount=amount/lst.size();
        for(Long id:lst){
            try {
                map.put(userRepository.findById(id).get(), individualAmount);
                
            } catch (Exception e) {
                throw error("no user found with id "+id);
            }
        }
        expense=Expense.builder().amount(amount).desc(des).splits(map).build();
        expenseRepository.save(expense);
    }

    // to get all the expense with their desciption and amount 
    @Override
    public void getAllExpense() {
        List<Expense> expenses=expenseRepository.findAll();
        for(Expense i : expenses){
            System.out.println("Des:" + i.getDesc() + " Amount:"+i.getAmount());
            for(Entry<User, Double> entry:i.getSplits().entrySet()){
                System.out.println("Name: "+entry.getKey().getName()+" Share:"+entry.getValue());
            }
        }
    }


    //  to getExpense Of Particular User
    @Override
    public void getExpenseById(Long id) {
        List<Expense> expenses=expenseRepository.findAll();
        Map<String,Double> map= new HashMap<>();
        String name=null;
        for(Expense expense:expenses){
            for(Map.Entry<User,Double> entry : expense.getSplits().entrySet()){
                if(entry.getKey().getId()==id){
                    if(name==null)
                        name=entry.getKey().getName();
                    map.put(expense.getDesc(), entry.getValue());
                }
            }
        }
        Double total=(double) 0;
        for(Map.Entry<String,Double> entry : map.entrySet()){
            System.out.println("Des: "+entry.getKey()+" Amount:"+entry.getValue());
            total+=entry.getValue();
        }
        System.out.println(name+ "Total:"+total);

    }
    @Override
    public String generateBalanceSheetTxt() throws IOException {
        List<Expense> expenses = expenseRepository.findAll();
        StringBuilder builder = new StringBuilder(); 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        builder.append("Description\t\tUser\t\tAmount\n");
        builder.append("------------------------------------------------------------\n");
        Double total = (double) 0;
        for (Expense expense : expenses) {
            total+=expense.getAmount();
            builder.append(expense.getDesc()).append("\t\t\t");
            for(Map.Entry<User,Double> entry:expense.getSplits().entrySet()){
                builder.append(entry.getKey().getName()).append("\t\t")
                .append(entry.getValue()).append("\t\t").append("\n");
                builder.append("\t\t\t\t");
            }
            builder.append("\n\n");
        }
        builder.append("Total: \t\t\t\t\t : " +total );
        return builder.toString();
    }
    @Override
    public void addExpense(String des, Double amount, Map<Long, Double> exact) throws Exception {
        Expense expense;
        Map<User,Double> map=new HashMap<User,Double>();
        Double a=(double) 0;
        for(Map.Entry<Long,Double> e:exact.entrySet()){
            try {
                
                Optional<User> u=userRepository.findById(e.getKey());
                if(u.get()!=null)
                    map.put(u.get(),e.getValue());
            } catch (Exception exception) {
                throw error("no user found with id "+e.getKey());
            }
            a+=e.getValue();
        }
        System.out.println(a.equals(amount));
        if(!a.equals(amount)){
            throw error("Amount is not equal to exact total");
        }
        expense=Expense.builder().amount(amount).desc(des).splits(map).build();
        expenseRepository.save(expense);
    }

}
