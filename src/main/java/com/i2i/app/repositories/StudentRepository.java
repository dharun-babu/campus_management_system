package com.i2i.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.i2i.app.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    Student findByRollNumber(String rollNumber);
    void deleteByRollNumber(String rollNumber);
    boolean existsByRollNumber(String rollNumber);
}
