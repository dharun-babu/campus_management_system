package com.i2i.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.i2i.app.dto.UpdateRequestStudentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dto.CreateStudentRequestDto;
import com.i2i.app.dto.StudentResponseDto;
import com.i2i.app.mapper.MapperInterface;
import com.i2i.app.model.BankAccount;
import com.i2i.app.model.Grade;
import com.i2i.app.model.Student;
import com.i2i.app.model.Teacher;
import com.i2i.app.util.DateUtil;
import com.i2i.app.repositories.StudentRepository;

/**
 * This class containing business logic for managing student operations.
 * Handles creation, retrieval, updating, and deletion of students.
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
     *
     * <p>
     *     Retrieves details of all students stored in the system.
     * </p>
     *
     * @return List of {@link StudentResponseDto} containing details of all students.
     * @throws StudentException if an error occurs while fetching students.
     */
    public List<StudentResponseDto> getAllStudents() throws StudentException {
        try {
            log.debug("Fetching all student details");
            List<Student> students = studentRepository.findAll();
            log.debug("Successfully fetched {} students details", students.size());
            List<StudentResponseDto> responseStudents = mapperInterface.convertToStudentResponseDto(students);
            for (StudentResponseDto student : responseStudents) {
                student.setAge(DateUtil.calculateYearDifference(student.getStudentDob()));
            }
            return responseStudents;
        } catch (Exception e) {
            log.error("Error occurred while fetching all students: {}", e.getMessage());
            throw new StudentException("Failed to fetch all students", e);
        }
    }

    /**
     * <p>
     *    Retrieves details of a specific student based on the roll number.
     * </p>
     *
     * @param rollNumber The roll number of the student to retrieve.
     * @return {@link StudentResponseDto} containing details of the specified student.
     * @throws StudentException if the student with the provided roll number is not found or if an error occurs while fetching the student.
     */
    public StudentResponseDto getStudentByRollNumber(String rollNumber) throws StudentException {
        try {
            log.debug("Fetching student details for roll number: {}", rollNumber);
            Student student = studentRepository.findByRollNumber(rollNumber);
            if (student == null) {
                throw new StudentException("Student not found with roll number: " + rollNumber);
            }
            log.debug("Successfully fetched student details for roll number: {}", rollNumber);
            StudentResponseDto responseStudent = mapperInterface.convertToStudentResponseDto(student);
            responseStudent.setAge(DateUtil.calculateYearDifference(responseStudent.getStudentDob()));
            return responseStudent;
        } catch (Exception e) {
            log.error("Error occurred while fetching student with roll number {}: {}", rollNumber, e.getMessage());
            throw new StudentException("Failed to fetch student with roll number: " + rollNumber, e);
        }
    }

    /**
     * <p>
     *     Saves a new student with the provided details.
     * </p>
     *
     * @param createStudentRequestDto Details of the student to be saved.
     * @return {@link StudentResponseDto} containing details of the newly saved student.
     * @throws StudentException if an error occurs while saving the student.
     */
    @Transactional
    public StudentResponseDto saveStudent(CreateStudentRequestDto createStudentRequestDto) throws StudentException {
        try {
            log.debug("Saving new student details");
            Grade grade = gradeService.getGradeByStandard(createStudentRequestDto.getGrade().getStandard());
            BankAccount bankAccount = bankAccountService.saveBankAccount(createStudentRequestDto.getBankAccount());
            Set<Teacher> teachers = createTeachers(createStudentRequestDto.getSubjects());
            Student student = new Student(
                    createStudentRequestDto.getStudentName(),
                    createStudentRequestDto.getStudentDob(),
                    grade,
                    bankAccount,
                    teachers
            );
            student.setRollNumber(generateCustomStudentID(student));
            Student savedStudent = studentRepository.save(student);
            log.debug("Successfully saved student details: {}", savedStudent);
            StudentResponseDto responseDto =  mapperInterface.convertToStudentResponseDto(savedStudent);
            responseDto.setAge(DateUtil.calculateYearDifference(responseDto.getStudentDob()));
            return responseDto;
        } catch (Exception e) {
            throw new StudentException("Failed to save student", e);
        }
    }

    /**
     * <p>
     *     Updates details of an existing student with the provided roll number and updated details.
     * </p>
     *
     * @param rollNumber The roll number of the student to be updated.
     * @param updateRequestStudentDto Updated details of the student.
     * @return {@link StudentResponseDto} containing details of the updated student.
     * @throws StudentException if the student with the provided roll number is not found or if an error occurs while updating the student.
     */
    @Transactional
    public StudentResponseDto updateStudent(String rollNumber, UpdateRequestStudentDto updateRequestStudentDto) throws StudentException {
        try {
            log.debug("Updating student details for roll number: {}", rollNumber);
            Student existingStudent = studentRepository.findByRollNumber(rollNumber);
            if (null == existingStudent) {
                throw new StudentException("Student not found with roll number: " + rollNumber);
            }
            existingStudent.setStudentName(updateRequestStudentDto.getStudentName());
            existingStudent.setStudentDob(updateRequestStudentDto.getStudentDob());
            if (null != updateRequestStudentDto.getGrade() && updateRequestStudentDto.getGrade().getStandard() != existingStudent.getGrade().getStandard()) {
                Grade grade = gradeService.getGradeByStandard(updateRequestStudentDto.getGrade().getStandard());
                existingStudent.setGrade(grade);
            }
            if (updateRequestStudentDto.getSubjects() != null && !updateRequestStudentDto.getSubjects().isEmpty()) {
                Set<Teacher> existingTeachers = existingStudent.getTeachers();
                Set<Teacher> newTeachers = createTeachers(updateRequestStudentDto.getSubjects());
                // Add only new teachers who are not already in the student's subjects
                for (Teacher newTeacher : newTeachers) {
                    if (!existingTeachers.contains(newTeacher)) {
                        existingTeachers.add(newTeacher);
                    }
                }
                existingStudent.setTeachers(existingTeachers);
            }
            if (updateRequestStudentDto.getBankAccount() != null &&
                    updateRequestStudentDto.getBankAccount().getBankName() != null) {
                BankAccount bankAccount = bankAccountService.saveBankAccount(updateRequestStudentDto.getBankAccount());
                existingStudent.setBankAccount(bankAccount);
            }
            Student updatedStudent = studentRepository.save(existingStudent);
            log.debug("Successfully updated student details of roll number {} : {}", rollNumber, updatedStudent);
            return mapperInterface.convertToStudentResponseDto(updatedStudent);
        } catch (Exception e) {
            throw new StudentException("Failed to update student with roll number: " + rollNumber, e);
        }
    }

    /**
     * <p>
     *     Deletes the student with the provided roll number from the system.
     * </p>
     *
     * @param rollNumber The roll number of the student to be deleted.
     * @throws StudentException if the student with the provided roll number is not found or if an error occurs while deleting the student.
     */
    @Transactional
    public void deleteStudent(String rollNumber) throws StudentException {
        try {
            log.debug("Deleting student details for roll number: {}", rollNumber);
            Student student = studentRepository.findByRollNumber(rollNumber);
            if (student == null) {
                throw new StudentException("Student not found with roll number: " + rollNumber);
            }
            Grade grade = student.getGrade();
            grade.getStudents().remove(student);
            gradeService.modifyCountByStandardAndSection(grade.getStandard(), grade.getSection());
            for (Teacher teacher : student.getTeachers()) {
                teacher.getStudents().remove(student);
            }
            studentRepository.deleteByRollNumber(rollNumber);
            log.debug("Successfully deleted student details for roll number: {}", rollNumber);
        } catch (Exception e) {
            throw new StudentException("Failed to delete student with roll number: " + rollNumber, e);
        }
    }

    /**
     * <p>
     *     Checks if a student exists with the provided roll number.
     * </p>
     *
     * @param rollNumber The roll number to check.
     * @return true if a student with the provided roll number exists, false otherwise.
     */
    public boolean isIdExist(String rollNumber) {
        try {
            log.debug("Checking existence of student roll number: {}", rollNumber);
            return studentRepository.existsByRollNumber(rollNumber);
        } catch (Exception e) {
            throw new StudentException("Invalid roll number :"+ rollNumber, e);
        }
    }

    /**
     * <p>
     *     Creates a set of teachers based on the provided subjects.
     * </p>
     *
     * @param subjects Set of subjects for which teachers need to be created.
     * @return Set of {@link Teacher} details corresponding to the provided subjects.
     */
    private Set<Teacher> createTeachers(Set<String> subjects) {
        Set<Teacher> teachers = new HashSet<>();
        if (subjects != null) {
            for (String subject : subjects) {
                try {
                    Teacher teacher = teacherService.findTeacherBySubject(subject);
                    if (teacher != null) {
                        teachers.add(teacher);
                    } else {
                        log.warn("Teacher not found for subject: {}", subject);
                    }
                } catch (Exception e) {
                    throw new StudentException("Error occurred while fetching teacher for subject: " +subject, e);
                }
            }
        }
        return teachers;
    }

    /**
     * <p>
     *     Generates a custom student ID based on grade and bank account details.
     * </p>
     *
     * @param student The {@link Student} details for which to generate the custom ID.
     * @return String containing the custom generated student ID.
     */
    public static String generateCustomStudentID(Student student) {
        String grade = student.getGrade().getGradeId().toString().replaceAll("-", "").substring(0, 2);
        String bankAccount = student.getBankAccount().getAccountId().toString().replaceAll("-", "").substring(0, 2);
        // Combine parts to form the custom student roll number (Ex : 12A12S12)
        //return String.format("%d%c%s%s%d", student.getGrade().getStandard(), student.getGrade().getSection(), grade, bankAccount, student.getGrade().getCountOfStudent());
        StringBuilder rollNumber = new StringBuilder();
        rollNumber.append(student.getGrade().getStandard())
                .append(student.getGrade().getSection())
                .append(grade)
                .append(bankAccount)
                .append(student.getGrade().getCountOfStudent());
        return rollNumber.toString();
    }
}
