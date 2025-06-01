package com.uken.api.rest.repostitory;

import com.uken.api.entity.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicesRepository extends JpaRepository<Services, Long> {
    List<Services> findAllByEmail(String email);

    @Query("SELECT s.serviceType, SUM(s.total) " +
            "FROM Services s " +
            "WHERE YEAR(s.date) = :year AND s.email = :email " +
            "GROUP BY s.serviceType")
    List<Object[]> findYearlyServicesByTypeAndEmail(@Param("year") int year, @Param("email") String email);

    @Query("SELECT s.serviceType, SUM(s.total) " +
            "FROM Services s " +
            "WHERE YEAR(s.date) = :year AND MONTH(s.date) = :month AND s.email = :email " +
            "GROUP BY s.serviceType")
    List<Object[]> findMonthlyServicesByTypeAndEmail(@Param("year") int year, @Param("month") int month, @Param("email") String email);
}
