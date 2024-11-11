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
}
