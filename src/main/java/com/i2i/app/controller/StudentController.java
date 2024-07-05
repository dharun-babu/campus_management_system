package com.i2i.app.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dto.CreateStudentRequestDto;
import com.i2i.app.dto.StudentResponseDto;
import com.i2i.app.service.StudentService;

/**
 * Handles all student-related operations including adding, retrieving, and deleting students.
 */
@RestController
@RequestMapping("cms/api/1.0.0/students")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;

    /**
     * <p>Adds a new student based on the provided student details.</p>
     * @param createStudentRequestDto Contains the details of the student to be added.
     * @return ResponseEntity containing the added student's details and HTTP status.
     */
    @PostMapping
    public ResponseEntity<StudentResponseDto> addStudent(@RequestBody CreateStudentRequestDto createStudentRequestDto) {
        try {
            log.info("Adding student");
            StudentResponseDto response = studentService.saveStudent(createStudentRequestDto);
            log.debug("Student added with details: {}", response);
            log.info("Successfully added the student");
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (StudentException e) {
            log.error("Error adding student: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("Unexpected error adding student: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * <p>Retrieves a list of all students.</p>
     * @return ResponseEntity containing the list of all students and HTTP status.
     */
    @GetMapping
    public ResponseEntity<List<StudentResponseDto>> getAllStudents() {
        log.debug("Retrieving all students");
        try {
            List<StudentResponseDto> students = studentService.getAllStudents();
            log.debug("Retrieved {} students", students.size());
            log.info("Successfully retrieved {} students details", students.size());
            return ResponseEntity.status(HttpStatus.OK).body(students);
        } catch (Exception e) {
            log.error("Error retrieving students: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * <p>Retrieves the details of a specific student based on the provided student ID.</p>
     * @param id The ID of the student to be retrieved.
     * @return ResponseEntity containing the details of the specified student and HTTP status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentResponseDto> getStudentById(@PathVariable("id") int id) {
        try {
            log.debug("Retrieving student with ID: {}", id);
            StudentResponseDto student = studentService.getStudentById(id);
            log.debug("Retrieved student details: {}", student);
            log.info("Successfully retrieved the student details by ID : {}", id);
            return ResponseEntity.status(HttpStatus.OK).body(student);
        } catch (Exception e) {
            log.error("Error retrieving student by ID: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * <p>Deletes a specific student based on the provided student ID.</p>
     * @param id The ID of the student to be deleted.
     * @return ResponseEntity with HTTP status indicating the result of the operation.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable("id") int id) {
        try {
            log.debug("Deleting student with ID: {}", id);
            studentService.deleteStudent(id);
            log.info("Successfully deleted student by  ID: {}", id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error("Error deleting student: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
