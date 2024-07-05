package com.i2i.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.slf4j.Slf4j;

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
 * This class handles the operations related to student management.
 */
@Service
@Slf4j
public class StudentService {

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
            log.debug("Star to fetching all student details");
            List<Student> students = studentRepository.findAll();
            log.debug("Successfully fetch {} students details", students.size());
            return mapperInterface.convertToStudentResponseDto(students);
        } catch (Exception e) {
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
            log.debug("Fetching Id : {} details from the database", id);
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new StudentException("Unable to retrieve student details by ID: " + id));
            log.debug("Successfully fetched details of {}", student);
            return mapperInterface.convertToStudentResponseDto(student);
        } catch (Exception e) {
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
    @Transactional
    public StudentResponseDto saveStudent(CreateStudentRequestDto createStudentRequestDto) throws StudentException {
        try {
            log.debug("Saving new student details");
            Grade grade = gradeService.getGradeByStandard(createStudentRequestDto.getCreateGradeRequestDto().getStandard());
            log.debug("Successfully get grade from the grade service : {} ", grade);
            BankAccountResponseDto bankAccountResponseDto = bankAccountService.saveBankAccount(createStudentRequestDto.getCreateBankAccountRequestDto());
            log.debug("Successfully get bankaccount from bank account service : {}", bankAccountResponseDto);
            Set<Teacher> teachers = new HashSet<>();
            if (!createStudentRequestDto.getSubjects().isEmpty()) {
                for (String subject : createStudentRequestDto.getSubjects()) {
                    teachers.add(teacherService.findTeacherBySubject(subject));
                }
            }
            Student student = new Student(createStudentRequestDto.getStudentName(), createStudentRequestDto.getStudentDob(), grade, mapperInterface.convertToBankAccount(bankAccountResponseDto), teachers);
            Student savedStudent = studentRepository.save(student);
            log.info("Inserted the student in database");
            return mapperInterface.convertToStudentResponseDto(savedStudent);
        } catch (Exception e) {
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
            log.debug("Deleting student details for id: {}", id);
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new StudentException("Unable to retrieve student details by ID: " + id));
            log.debug("Retrieved the student to modify the count of students ");
            gradeService.modifyCountByStandardAndSection(student.getGrade().getStandard(), student.getGrade().getSection());
            studentRepository.deleteById(id);
            log.debug("Removed student from the database");
        } catch (Exception e) {
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
            log.debug("Checking existence of student id: {}", id);
            return studentRepository.existsById(id);
        } catch (Exception e) {
            throw new StudentException("Unable to check the existence of ID: " + id, e);
        }
    }
}
