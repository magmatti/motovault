package com.uken.api.rest.repostitory;

import com.uken.api.entity.Expenses;
import com.uken.api.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpensesRepository extends JpaRepository<Expenses, Long> {
    List<Expenses> findAllByEmail(String email);

    @Query("SELECT e.expensesType, SUM(e.total) " +
            "FROM Expenses e " +
            "WHERE YEAR(e.date) = :year AND e.email = :email " +
            "GROUP BY e.expensesType")
    List<Object[]> findYearlyExpensesByTypeAndEmail(@Param("year") int year, @Param("email") String email);

    @Query("SELECT e.expensesType, SUM(e.total) " +
            "FROM Expenses e " +
            "WHERE YEAR(e.date) = :year AND MONTH(e.date) = :month AND e.email = :email " +
            "GROUP BY e.expensesType")
    List<Object[]> findMonthlyExpensesByTypeAndEmail(@Param("year") int year, @Param("month") int month, @Param("email") String email);
}
