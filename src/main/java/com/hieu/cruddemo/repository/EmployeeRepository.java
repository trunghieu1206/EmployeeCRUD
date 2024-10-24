package com.hieu.cruddemo.repository;

import com.hieu.cruddemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "SELECT * FROM employees", nativeQuery = true)
    List<Employee> findAll();

    @Query(value = "SELECT * FROM employees WHERE id = :id", nativeQuery = true)
    Employee findById(@Param("id") int theId);

    @Modifying
    @Query(value = "DELETE FROM employees WHERE id = :id", nativeQuery = true)
    void deleteById(@Param("id") int theId);
}
