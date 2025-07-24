package com.example.backend_revision.repo;

import com.example.backend_revision.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmpRepo extends JpaRepository<Employee, Long> {
    List<Employee> findByTsGreaterThan(Long ts);
}
