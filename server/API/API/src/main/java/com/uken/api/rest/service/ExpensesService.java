package com.uken.api.rest.service;

import com.uken.api.entity.Expenses;
import com.uken.api.rest.repostitory.ExpensesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpensesService {
    @Autowired
    private ExpensesRepository expensesRepository;

    public Expenses saveExpenses(Expenses expenses) {
        return expensesRepository.save(expenses);
    }

    public List<Expenses> getAllExpenses() {
        return expensesRepository.findAll();
    }

    public Optional<Expenses> getExpensesById(long id) {
        return expensesRepository.findById(id);
    }

    public void deleteExpenseById(long id) {
        expensesRepository.deleteById(id);
    }

    public List<Expenses> getExpensesByEmail(String email) {return expensesRepository.findAllByEmail(email);}

    public List<Object[]> getYearlyExpensesByTypeAndEmail(int year, String email) {
        return expensesRepository.findYearlyExpensesByTypeAndEmail(year, email);
    }

    public List<Object[]> getMonthlyExpensesByTypeAndEmail(int year, int month, String email) {
        return expensesRepository.findMonthlyExpensesByTypeAndEmail(year, month, email);
    }

    public Expenses updateExpense(long id, Expenses updatedExpense) {
        return expensesRepository.findById(id).map(expense -> {
            expense.setVehicleID(updatedExpense.getVehicleID());
            expense.setExpensesType(updatedExpense.getExpensesType());
            expense.setDate(updatedExpense.getDate());
            expense.setTotal(updatedExpense.getTotal());
            expense.setMail(updatedExpense.getMail());
            return expensesRepository.save(expense);
        }).orElseThrow(() -> new IllegalArgumentException("Expense with ID " + id + " not found"));
    }
}
