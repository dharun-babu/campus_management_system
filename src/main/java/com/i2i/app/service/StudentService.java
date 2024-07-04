package com.i2i.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.i2i.app.dto.CreateStudentRequestDto;
import com.i2i.app.dto.StudentResponseDto;
import com.i2i.app.dto.BankAccountResponseDto;
import com.i2i.app.mapper.MapperInterface;
import com.i2i.app.model.Teacher;
import com.i2i.app.model.Student;
import com.i2i.app.model.Grade;
import com.i2i.app.repositories.StudentRepository;
import com.i2i.app.customexception.StudentException;

/**
 * <p>
 * This class handles the operations related to student management.
 * </p>
 */
@Service
public class StudentService {

    private static final Logger logger = LoggerFactory.getLogger(StudentService.class);

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GradeService gradeService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private MapperInterface mapperInterface;

    /**
     * <p>
     * Retrieves all students and maps them to response DTOs.
     * </p>
     *
     * @return List of StudentResponseDto
     * @throws StudentException if unable to retrieve student details
     */
    public List<StudentResponseDto> getAllStudents() throws StudentException {
        try {
            logger.info("Fetching all student details");
            List<Student> students = studentRepository.findAll();
            return mapperInterface.convertToStudentResponseDto(students);
        } catch (Exception e) {
            logger.error("Error retrieving all student details", e);
            throw new StudentException("Unable to retrieve all student details", e);
        }
    }

    /**
     * <p>
     * Retrieves a student by their unique identifier and maps to a response DTO.
     * </p>
     *
     * @param id The unique identifier of the student
     * @return StudentResponseDto if found
     * @throws StudentException if unable to retrieve the student by id
     */
    public StudentResponseDto getStudentById(int id) throws StudentException {
        try {
            logger.info("Fetching student details for id: {}", id);
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new StudentException("Unable to retrieve student details by ID: " + id));
            return mapperInterface.convertToStudentResponseDto(student);
        } catch (Exception e) {
            logger.error("Error retrieving student by id: {}", id, e);
            throw new StudentException("Unable to retrieve student details by ID: " + id, e);
        }
    }

    /**
     * <p>
     * Saves a new student along with their associated grade, bank account, and teachers.
     * </p>
     *
     * @param createStudentRequestDto The student details to be saved
     * @return StudentResponseDto of the saved student
     * @throws StudentException if unable to save the student details
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public StudentResponseDto saveStudent(CreateStudentRequestDto createStudentRequestDto) throws StudentException {
        try {
            logger.info("Saving new student details");
            Grade grade = gradeService.getGradeByStandard(createStudentRequestDto.getCreateGradeRequestDto().getStandard());
            BankAccountResponseDto bankAccountResponseDto = bankAccountService.saveBankAccount(createStudentRequestDto.getCreateBankAccountRequestDto());
            Set<Teacher> teachers = new HashSet<>();
            if (!createStudentRequestDto.getSubjects().isEmpty()) {
                for (String subject : createStudentRequestDto.getSubjects()) {
                    teachers.add(teacherService.findTeacherBySubject(subject));
                }
            }
            Student student = new Student(createStudentRequestDto.getStudentName(), createStudentRequestDto.getStudentDob(), grade, mapperInterface.convertToBankAccount(bankAccountResponseDto), teachers);
            Student savedStudent = studentRepository.save(student);
            logger.info("Saved new student details for id: {}", savedStudent.getStudentId());
            return mapperInterface.convertToStudentResponseDto(savedStudent);
        } catch (Exception e) {
            logger.error("Error saving student details", e);
            throw new StudentException("Unable to save the student details", e);
        }
    }

    /**
     * <p>
     * Deletes a student by their unique identifier.
     * </p>
     *
     * @param id The unique identifier of the student
     * @throws StudentException if unable to delete the student
     */
    public void deleteStudent(int id) throws StudentException {
        try {
            logger.info("Deleting student details for id: {}", id);
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new StudentException("Unable to retrieve student details by ID: " + id));
            gradeService.modifyCountByStandardAndSection(student.getGrade().getStandard(), student.getGrade().getSection());
            studentRepository.deleteById(id);
            logger.info("Deleted student details for id: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting student by id: {}", id, e);
            throw new StudentException("Unable to delete the student details by ID: " + id, e);
        }
    }

    /**
     * <p>
     * Checks if a student with the given unique identifier exists.
     * </p>
     *
     * @param id The unique identifier of the student
     * @return true if the student exists, false otherwise
     * @throws StudentException if unable to check the existence of the student by id
     */
    public boolean isIdExist(int id) throws StudentException {
        try {
            logger.debug("Checking existence of student id: {}", id);
            return studentRepository.existsById(id);
        } catch (Exception e) {
            logger.error("Error checking existence of student id: {}", id, e);
            throw new StudentException("Unable to check the existence of ID: " + id, e);
        }
    }
}
