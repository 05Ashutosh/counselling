package com.ashu.counsellingbackend.repository;

import com.ashu.counsellingbackend.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    // Custom query methods can be added here if needed
    List<Student> findByInterestedTrue();

}
