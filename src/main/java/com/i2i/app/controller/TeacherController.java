package com.i2i.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import lombok.extern.slf4j.Slf4j;

import com.i2i.app.dto.TeacherResponseDto;
import com.i2i.app.service.TeacherService;


/**
 * This class for managing teacher-related operations.
 * This class provides endpoints to create, retrieve, and delete teacher details,
 * along with retrieving teacher details based on subject name.
 */
@RestController
@RequestMapping("cms/api/v1/teachers")
@Slf4j
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    /**
     * <p>
     *     This method handles HTTP GET requests to retrieve all teacher details.
     * </p>
     *
     * @return ResponseEntity containing the list of all teachers or an error status.
     */
    @GetMapping
    public ResponseEntity<?> getAllTeachers() {
        log.info("Displaying all teachers");
        try {
            List<TeacherResponseDto> teachers = teacherService.getAllTeachers();
            log.info("Displayed the {} teacher details ",teachers.size());
            return ResponseEntity.status(HttpStatus.OK).body(teachers);
        } catch (Exception e) {
            log.error("Error displaying teachers: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to fetch all teacher details");
        }
    }

    /**
     * <p>
     *    This method handles HTTP GET requests to retrieve a teacher's details based on their ID.
     * </p>
     *
     * @param id The ID of the teacher to retrieve.
     * @return ResponseEntity containing the teacher details or an error status.
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable("id") int id) {
        log.info("Displaying teacher by ID : {}", id);
        try {
            TeacherResponseDto teacher = teacherService.getTeacherById(id);
            log.info("Successfully retrieved the teacher details ");
            return ResponseEntity.status(HttpStatus.OK).body(teacher);
        } catch (Exception e) {
            log.error("Error displaying teacher by ID: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("unable to fetch teacher by id");
        }
    }

    /**
     * <p>
     *     This method handles HTTP GET requests to retrieve a teacher's details based on their subject name.
     * </p>
     *
     * @param subject The subject name of the teacher to retrieve.
     * @return ResponseEntity containing the teacher details or an error status.
     */
    @GetMapping("/{subject}")
    public ResponseEntity<TeacherResponseDto> getTeacherBySubject(@PathVariable("subject") String subject) {
        try {
            TeacherResponseDto teacher = teacherService.getTeacherBySubject(subject);
            return ResponseEntity.status(HttpStatus.OK).body(teacher);
        } catch (Exception e) {
            log.error("Error displaying teacher by subject: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * <p>
     *     This method handles HTTP DELETE requests to delete a teacher based on their ID.
     * </p>
     *
     * @param id The ID of the teacher to delete.
     * @return ResponseEntity with no content status or an error status.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacherById(@PathVariable("id") int id) {
        try {
            teacherService.deleteTeacher(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            log.error("Error deleting teacher: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
