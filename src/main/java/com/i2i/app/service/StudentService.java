package com.i2i.app.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dao.StudentDAO;
import com.i2i.app.model.BankAccount;
import com.i2i.app.model.Grade;
import com.i2i.app.model.Student;
import com.i2i.app.model.Teacher;
/**
 * This class provides a comprehensive managing student-related operations.
 * This includes adding new students, retrieving existing students, and removing students from the system.
 * It interacts with the storage layer class to perform CRUD operations and collaborates with other service classes
 * to handle related functionalities.
 */
@Service
public class StudentService {

    private static final Logger logger = LogManager.getLogger(StudentService.class);

    @Autowired
    private GradeService gradeService;

    @Autowired
    private BankAccountService bankAccountService;

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private StudentDAO studentDAO;

    /**
     * <p>Adds a new student to the system.
     * This method creates a new Student and associates it with the appropriate grade, bank account, and teachers.
     * It then persists the Student to fetched using the StudentDAO.</p>
     *
     * @param studentName   the name of the student; must be a non-null, non-empty string.
     * @param studentdob    the date of birth of the student in format (yyyy-mm-dd); must be a valid Date.
     * @param standard      the grade level of the student; must be an integer in the range (1-12).
     * @param bankAccount   the bank account associated with the student; must be a valid BankAccount.
     * @param teachers      the set of teachers assigned to the student; must be a non-null Set of Teacher.
     * @return the newly created Student.
     */
    public Student addStudent(String studentName, Date studentdob, int standard, BankAccount bankAccount, Set<Teacher> teachers) throws StudentException {
        logger.debug("Adding a new student with name: {}, DOB: {}, Standard: {}", studentName, studentdob, standard);
        Grade grade = gradeService.getOrCreateGrade(standard);
        if (grade == null) {
            logger.warn("Failed to get or create grade for standard: {}", standard);
        }
        Student student = new Student(studentName, studentdob, grade, bankAccount, teachers);
        studentDAO.insertStudent(student);
        logger.debug("Successfully added student : {}", student);
        return student;
    }

    /**
     * <p>Retrieves all students from the system.
     * This method fetches a list of all students fetched using the StudentDAO.</p>
     *
     * @return a List of all Students present in the system.
     */
    public List<Student> getAllStudents() throws StudentException {
        logger.debug("Retrieving all students");
        List<Student> students = studentDAO.getAllStudents();
        logger.debug("Retrieved {} students", students.size());
        return students;
    }

    /**
     * <p>Retrieves a student by their ID.
     * This method fetches a student with the specified ID fetched using the StudentDAO.
     * If the student is not found, it returns null.</p>
     *
     * @param studentId the ID of the student to retrieve; must be a positive integer.
     * @return the Student object with the given ID, or null if no such student exists.
     */
    public Student getStudent(int studentId) throws StudentException {
        logger.debug("Retrieving student with ID: {}", studentId);
        Student student = studentDAO.getStudentById(studentId);
        if (student == null) {
            logger.warn("No student found with ID: {}", studentId);
        } else {
            logger.debug("Successfully retrieved student: {}", student);
        }
        return student;
    }

    /**
     * <p>Removes a student by their ID.
     * This method deletes the student with the specified ID.
     * If the student is successfully removed, it returns true. Otherwise, it returns false.</p>
     *
     * @param studentId the ID of the student to remove; must be a positive integer.
     * @return true if the student was successfully removed, false otherwise.
     */
    public boolean removeStudentById(int studentId) throws StudentException {
        logger.debug("Removing student with ID: {}", studentId);
        boolean isDeleted = studentDAO.deleteStudentById(studentId);
        if (isDeleted) {
            logger.debug("Successfully removed student with ID: {}", studentId);
        } else {
            logger.warn("Failed to remove student with ID: {}", studentId);
        }
        return isDeleted;
    }

    /**
     * <p>Checks if an account number already exists in the system.
     * This method verifies if the given bank account number is already present in the system</p>
     *
     * @param accountNumber the account number to check; must be a positive long value.
     * @return true if the account number exists, false otherwise.
     */
    public boolean isAccountNumberExists(long accountNumber) throws StudentException {
        logger.debug("Checking if account number exists: {}", accountNumber);
        boolean exists = bankAccountService.isAccountNumberExists(accountNumber);
        logger.debug("Account number {} existence: {}", accountNumber, exists);
        return exists;
    }

    /**
     * <p>Retrieves a bank account with the given details.
     * This method creates and returns a BankAccount using the provided bank details.</p>
     *
     * @param bankName      the name of the bank; must be a non-null, non-empty string
     * @param branchName    the name of the branch; must be a non-null, non-empty string
     * @param accountNumber the account number; must be a positive long value
     * @param ifscCode      the IFSC code of the branch; must be a non-null, non-empty string
     * @param mobileNumber  the mobile number associated with the account; must be a positive long value
     * @return the BankAccount created with the provided details
     */
    public BankAccount getBankAccount(String bankName, String branchName, long accountNumber, String ifscCode, long mobileNumber) {
        logger.debug("Retrieving bank account for Bank: {}, Branch: {}, AccountNumber: {}", bankName, branchName, accountNumber);
        BankAccount bankAccount = bankAccountService.getBankAccount(bankName, branchName, accountNumber, ifscCode, mobileNumber);
        logger.debug("Retrieved bank account: {}", bankAccount);
        return bankAccount;
    }

    /**
     * <p>Retrieves the set of teachers for a given standard.
     * This method fetches the set of teachers assigned to the given standard and group option</p>
     *
     * @param subjectsOption the subjects option for the standard; must be an integer value representing the subjects.
     * @param groupOption    the group option for the standard; must be an integer value representing the group.
     * @return the set of Teacher assigned to the given standard and group option.
     */
    public Set<Teacher> getTeachersForStandard(int subjectsOption, int groupOption) throws StudentException {
        logger.debug("Retrieving teachers for subjectsOption: {}, groupOption: {}", subjectsOption, groupOption);
        Set<Teacher> teachers = teacherService.getTeachersForStandard(subjectsOption, groupOption);
        if (teachers == null || teachers.isEmpty()) {
            logger.warn("No teachers found for subjectsOption: {}, groupOption: {}", subjectsOption, groupOption);
        } else {
            logger.debug("Retrieved {} teachers for subjectsOption: {}, groupOption: {}", teachers.size(), subjectsOption, groupOption);
        }
        return teachers;
    }
}
