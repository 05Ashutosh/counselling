package com.ashu.counsellingbackend.controller;

import com.ashu.counsellingbackend.model.Student;
import com.ashu.counsellingbackend.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            log.info("Fetched all students successfully");
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error while fetching all students: {}", ex.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        try {
            Optional<Student> student = studentService.getStudentById(id);
            if (student.isPresent()) {
                log.info("Student with ID {} found", id);
                return ResponseEntity.ok(student.get());
            } else {
                log.warn("Student with ID {} not found", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (Exception ex) {
            log.error("Error while fetching student by ID {}: {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("createStudent")
    public ResponseEntity<Student> createStudent(@RequestBody Student student) {
        try {
            Student savedStudent = studentService.saveStudent(student);
            log.info("Created new student with ID {}", savedStudent.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(savedStudent);
        } catch (Exception ex) {
            log.error("Error while creating student: {}", ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            Optional<Student> existingStudent = studentService.getStudentById(id);
            if (!existingStudent.isPresent()) {
                log.warn("Student with ID {} not found for update", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            student.setId(id);
            Student updatedStudent = studentService.saveStudent(student);
            log.info("Updated student with ID {}", id);
            return ResponseEntity.ok(updatedStudent);
        } catch (Exception ex) {
            log.error("Error while updating student with ID {}: {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        try {
            Optional<Student> existingStudent = studentService.getStudentById(id);
            if (!existingStudent.isPresent()) {
                log.warn("Student with ID {} not found for deletion", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            studentService.deleteStudent(id);
            log.info("Deleted student with ID {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception ex) {
            log.error("Error while deleting student with ID {}: {}", id, ex.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
