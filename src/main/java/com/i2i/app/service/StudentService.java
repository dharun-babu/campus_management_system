package com.i2i.app.service;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
public class StudentService {

    private GradeService gradeService = new GradeService();
    private BankAccountService bankAccountService = new BankAccountService();
    private TeacherService teacherService = new TeacherService();
    private StudentDAO studentDAO = new StudentDAO();

    /**
     * <p> Adds a new student to the system.
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
        Grade grade = gradeService.getOrCreateGrade(standard);
        Student student = new Student(studentName, studentdob, grade, bankAccount, teachers);
        studentDAO.insertStudent(student);
        return student;
    }

    /**
     * <p> Retrieves all students from the system.
     * This method fetches a list of all students fetched using the StudentDAO.</p>
     *
     * @return a List of all Students present in the system.
     */
    public List<Student> getAllStudents() throws StudentException {
        return studentDAO.getAllStudents();
    }

    /**
     * <p> Retrieves a student by their ID.
     * This method fetches a student with the specified ID fetched using the StudentDAO.
     * If the student is not found, it returns null.</p>
     *
     * @param studentId     the ID of the student to retrieve; must be a positive integer.
     * @return the Student object with the given ID, or null if no such student exists.
     */
    public Student getStudent(int studentId) throws StudentException {
        return studentDAO.getStudentById(studentId);
    }

    /**
     * <p> Removes a student by their ID.
     * This method deletes the student with the specified ID from the  using the StudentDAO.
     * If the student is successfully removed, it returns true. Otherwise, it returns false.</p>
     *
     * @param studentId     the ID of the student to remove; must be a positive integer.
     * @return true if the student was successfully removed, false otherwise.
     */
    public boolean removeStudentById(int studentId) throws StudentException {
        return studentDAO.deleteStudentById(studentId);
    }

    /**
     * <p> Checks if an account number already exists in the system.
     * This method verifies if the given bank account number is already present in the system using the BankAccountService.</p>
     *
     * @param accountNumber the account number to check; must be a positive long value.
     * @return true if the account number exists, false otherwise.
     */
    public boolean isAccountNumberExists(long accountNumber) throws StudentException {
        return bankAccountService.isAccountNumberExists(accountNumber);
    }

    /**
     * <p> Retrieves a bank account with the given details.
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
        return bankAccountService.getBankAccount(bankName, branchName, accountNumber, ifscCode, mobileNumber);
    }

    /**
     * <p> Retrieves the set of teachers for a given standard.
     * This method fetches the set of teachers assigned to the given standard and group option using the TeacherService.</p>
     *
     * @param subjectsOption the subjects option for the standard; must be an integer value representing the subjects.
     * @param groupOption    the group option for the standard; must be an integer value representing the group.
     * @return the set of Teacher assigned to the given standard and group option.
     */
    public Set<Teacher> getTeachersForStandard(int subjectsOption, int groupOption) throws StudentException {
        return teacherService.getTeachersForStandard(subjectsOption, groupOption);
    }
}
