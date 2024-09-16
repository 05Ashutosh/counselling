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
@RequestMapping("/admin")
public class AdminController {

    private final StudentService studentService;

    public AdminController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/get/allUsers")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<Student> students = studentService.getAllStudents();
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error while fetching all users: {}", ex.getMessage());
            return new ResponseEntity<>("An error occurred while fetching all users.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/allocateBranch/{studentId}")
    public ResponseEntity<String> allocateBranch(@PathVariable Long studentId, @RequestBody String branch) {
        try {
            Optional<Student> studentOptional = studentService.getStudentById(studentId);

            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                student.setAllocatedBranch(branch);
                studentService.saveStudent(student);
                return new ResponseEntity<>("Branch allocated successfully", HttpStatus.OK);
            } else {
                log.warn("Student with ID {} not found", studentId);
                return new ResponseEntity<>("Student not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception ex) {
            log.error("Error while allocating branch for student with ID {}: {}", studentId, ex.getMessage());
            return new ResponseEntity<>("An error occurred while allocating branch.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/interestedStudent")
    public ResponseEntity<?> getInterestedStudent() {
        try {
            List<Student> interestedStudents = studentService.getAllInterestedStudents();

            if (interestedStudents.isEmpty()) {
                log.info("No interested students found.");
                return new ResponseEntity<>("No interested students found.", HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(interestedStudents, HttpStatus.OK);
        } catch (Exception ex) {
            log.error("Error while retrieving interested students: {}", ex.getMessage());
            return new ResponseEntity<>("An error occurred while retrieving interested students.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
