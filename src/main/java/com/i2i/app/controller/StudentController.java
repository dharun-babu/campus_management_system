package com.i2i.app.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.i2i.app.customexception.ValidationException;
import com.i2i.app.util.DateUtil;
import com.i2i.app.util.StringUtil;
import com.i2i.app.util.ValidationUtil;
import com.i2i.app.customexception.StudentException;
import com.i2i.app.dto.CreateStudentRequestDto;
import com.i2i.app.dto.StudentResponseDto;
import com.i2i.app.dto.UpdateRequestStudentDto;
import com.i2i.app.service.StudentService;

/**
 * This class manages operations related to students including addition, retrieval, updating, and deletion.
 * Provides REST endpoints for these operations.
 */
@RestController
@RequestMapping("cms/api/v1/students")
@Slf4j
public class StudentController {

    @Autowired
    private StudentService studentService;
    /**
     * <p>
     * Adds a new student based on the provided student details.
     * For validation of the date, see {@link com.i2i.app.controller.StudentController#validateCreateStudentRequest(CreateStudentRequestDto)}.
     * This method calls {@link com.i2i.app.service.StudentService#saveStudent(CreateStudentRequestDto)} to save the student.
     * </p>
     *
     * @param createStudentRequestDto Contains the details of the student to be added.
     * @return ResponseEntity containing the added student's details and HTTP status.
     */
    @PostMapping
    public ResponseEntity<?> addStudent(@RequestBody CreateStudentRequestDto createStudentRequestDto) {
        try {
            validateCreateStudentRequest(createStudentRequestDto);
            StudentResponseDto response = studentService.saveStudent(createStudentRequestDto);
            log.debug("Student added successfully with details: {}", response);
            log.info("Successfully added a new student with roll number : {}",response.getRollNumber());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (StudentException e) {
            log.error("Error adding a student: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (ValidationException e) {
            log.error("Validation error adding a student: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    /**
     * <p>
     *     Retrieves a list of all students.
     *     Fetching of all student calls method {@link com.i2i.app.service.StudentService#}.
     * </p>
     *
     * @return ResponseEntity containing the list of all students and HTTP status.
     */
    @GetMapping
    public ResponseEntity<?> getAllStudents() {
        try {
            List<StudentResponseDto> students = studentService.getAllStudents();
            log.debug("Retrieved {} students", students.size());
            log.info("Successfully retrieved {} students details", students.size());
            return ResponseEntity.status(HttpStatus.OK).body(students);
        } catch (StudentException e) {
            log.error("Error retrieving all students: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * <p>
     *     Retrieves the details of a specific student based on the provided student roll number.
     *     Check the roll number is exist or not and then fetch the student details by roll number.
     *     Fetched by {@link com.i2i.app.service.StudentService#getStudentByRollNumber(String)}
     * </p>
     *
     * @param rollNumber The ID of the student to be retrieved.
     * @return ResponseEntity containing the details of the specified student and HTTP status.
     */
    @GetMapping("/{rollNumber}")
    public ResponseEntity<?> getStudentByRollNumber(@PathVariable("rollNumber") String rollNumber) {
        try {
            log.debug("Retrieving student with roll number: {}", rollNumber);
            if (studentService.isIdExist(rollNumber)) {
                StudentResponseDto student = studentService.getStudentByRollNumber(rollNumber);
                log.debug("Retrieved student details: {}", student);
                log.info("Successfully retrieved the details of student with roll number : {}", rollNumber);
                return ResponseEntity.status(HttpStatus.OK).body(student);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (StudentException e) {
            log.error("Error retrieving student with roll number {}: {}", rollNumber, e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    /**
     * <p>
     *     Updates a specific student based on the provided student roll number and details.
     * </p>
     *
     * @param rollNumber The ID of the student to be updated.
     * @param student Contains the details of the student to be updated.
     * @return ResponseEntity containing the updated student's details and HTTP status.
     */
    @PutMapping("/{rollNumber}")
    public ResponseEntity<?> updateStudent(@PathVariable("rollNumber") String rollNumber, @RequestBody UpdateRequestStudentDto student) {
        try {
            log.debug("Updating student with roll number : {}", rollNumber);
            if (studentService.isIdExist(rollNumber)) {
                StudentResponseDto updatedStudent = studentService.updateStudent(rollNumber, student);
                log.debug("Updated student details: {}", updatedStudent);
                log.info("Successfully updated the details of student with roll number: {}", rollNumber);
                return ResponseEntity.status(HttpStatus.OK).body(updatedStudent);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (StudentException e) {
            log.error("Error updating student with roll number {} : {}", rollNumber, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        } catch (ValidationException e) {
            log.error("Validation error updating student with roll number {}: {}", rollNumber, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    /**
     * <p>
     *     Deletes a specific student based on the provided student roll number.
     * </p>
     *
     * @param rollNumber The ID of the student to be deleted.
     * @return ResponseEntity with HTTP status indicating the result of the operation.
     */
    @DeleteMapping("/{rollNumber}")
    public ResponseEntity<?> deleteStudentById(@PathVariable("rollNumber") String rollNumber) {
        try {
            log.debug("Deleting student with roll number : {}", rollNumber);
            if (studentService.isIdExist(rollNumber)) {
                studentService.deleteStudent(rollNumber);
                log.info("Successfully deleted the student with roll number : {}", rollNumber);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (StudentException e) {
            log.error("Error deleting student with roll number {}: {}", rollNumber, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * <p>
     *    Validates the input data for creating a student.
     *</p>
     * @param createStudentRequestDto Contains the details of the student to be validated.
     * @throws ValidationException If any validation fails.
     */
    private void validateCreateStudentRequest(CreateStudentRequestDto createStudentRequestDto) throws ValidationException {
        if (!StringUtil.validateString(createStudentRequestDto.getStudentName())) {
            throw new ValidationException("Invalid name provided: " + createStudentRequestDto.getStudentName());
        }
        if (!DateUtil.isFormatDate(createStudentRequestDto.getStudentDob().toString())) {
            throw new ValidationException("Invalid Date provided: " + createStudentRequestDto.getStudentDob());
        }
        if (!ValidationUtil.isValidStandard(createStudentRequestDto.getGrade().getStandard())) {
            throw new ValidationException("Invalid standard provided: " + createStudentRequestDto.getGrade().getStandard());
        }
        if (!StringUtil.validateString(createStudentRequestDto.getBankAccount().getBankName())) {
            throw new ValidationException("Invalid bank name provided: " + createStudentRequestDto.getBankAccount().getBankName());
        }
        if (!StringUtil.validateString(createStudentRequestDto.getBankAccount().getBranchName())) {
            throw new ValidationException("Invalid branch name provided: " + createStudentRequestDto.getBankAccount().getBranchName());
        }
        if (!ValidationUtil.isValidAccountNumber(createStudentRequestDto.getBankAccount().getAccountNumber())) {
            throw new ValidationException("Invalid account number provided: " + createStudentRequestDto.getBankAccount().getAccountNumber());
        }
        if (!ValidationUtil.isValidIfscCode(createStudentRequestDto.getBankAccount().getIfscCode())) {
            throw new ValidationException("Invalid IFSC code provided: " + createStudentRequestDto.getBankAccount().getIfscCode());
        }
        if (!ValidationUtil.isValidPhoneNumber(createStudentRequestDto.getBankAccount().getMobileNumber())) {
            throw new ValidationException("Invalid mobile number provided: " + createStudentRequestDto.getBankAccount().getMobileNumber());
        }
        for (String subject : createStudentRequestDto.getSubjects()) {
            if (!StringUtil.isValidSubject(subject)) {
                throw new ValidationException("Invalid subject provided: " + subject);
            }
        }
    }
}
