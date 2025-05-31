package com.uken.api.rest.controller;

import com.uken.api.entity.Expenses;
import com.uken.api.rest.service.ExpensesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {

    @Autowired
    private ExpensesService expensesService;

    @PostMapping
    public ResponseEntity<Expenses> createExpense(@RequestBody Expenses expenses) {
        Expenses savedExpense = expensesService.saveExpenses(expenses);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Expenses>> getAllExpenses() {
        List<Expenses> expensesList = expensesService.getAllExpenses();
        return new ResponseEntity<>(expensesList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expenses> getExpenseById(@PathVariable long id) {
        Optional<Expenses> expense = expensesService.getExpensesById(id);
        return expense.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable long id) {
        expensesService.deleteExpenseById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

