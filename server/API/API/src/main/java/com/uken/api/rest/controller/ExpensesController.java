package com.uken.api.rest.controller;

import com.uken.api.entity.Expenses;
import com.uken.api.entity.Vehicle;
import com.uken.api.rest.service.ExpensesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/expenses")
public class ExpensesController {

    @Autowired
    private ExpensesService expensesService;

    @PostMapping
    public ResponseEntity<Expenses> createExpense(@Valid @RequestBody Expenses expenses) {
        Expenses savedExpense = expensesService.saveExpenses(expenses);
        return new ResponseEntity<>(savedExpense, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Expenses>> getAllExpenses() {
        List<Expenses> expensesList = expensesService.getAllExpenses();
        return new ResponseEntity<>(expensesList, HttpStatus.OK);
    }

    @GetMapping("/getAll/{email}")
    public ResponseEntity<List<Expenses>> getExpensesByEmail(@PathVariable String email) {
        List<Expenses> expenses = expensesService.getExpensesByEmail(email);
        if (expenses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(expenses, HttpStatus.OK);
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

    @GetMapping("/yearly/{year}/{email}")
    public ResponseEntity<?> getYearlyExpensesByTypeAndEmail(@PathVariable int year, @PathVariable String email) {
        List<Object[]> yearlyExpenses = expensesService.getYearlyExpensesByTypeAndEmail(year, email);
        List<Map<String, Object>> response = yearlyExpenses.stream()
                .map(expense -> Map.of(
                        "category", expense[0],
                        "value", expense[1]
                ))
                .toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/monthly/{year}/{month}/{email}")
    public ResponseEntity<?> getMonthlyExpensesByTypeAndEmail(@PathVariable int year, @PathVariable int month, @PathVariable String email) {
        List<Object[]> monthlyExpenses = expensesService.getMonthlyExpensesByTypeAndEmail(year, month, email);
        List<Map<String, Object>> response = monthlyExpenses.stream()
                .map(expense -> Map.of(
                        "category", expense[0],
                        "value", expense[1]
                ))
                .toList();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Expenses> updateExpense(
            @PathVariable long id,
            @Valid @RequestBody Expenses updatedExpense) {
        Optional<Expenses> existingExpense = expensesService.getExpensesById(id);
        if (existingExpense.isPresent()) {
            Expenses savedExpense = expensesService.updateExpense(id, updatedExpense);
            return new ResponseEntity<>(savedExpense, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

