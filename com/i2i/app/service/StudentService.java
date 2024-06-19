package com.i2i.app.service;

import java.util.ArrayList;
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
 * This class provides services related to student management, including adding, retrieving, and removing students. 
 * It interacts with various other services and perform its functions.
 */
public class StudentService {
	private GradeService gradeService = new GradeService();
	private BankAccountService bankAccountService = new BankAccountService();
	private TeacherService teacherService = new TeacherService();
	private StudentDAO studentDAO = new StudentDAO();

	/**
	 * <p>Adds a new student to the system.</p>
	 * 
	 * @param studentName  the name of the student
	 * @param studentdob  the date of birth of the student in format (yyyy-mm-dd)
	 * @param standard  the grade level of the student in range (1-12)
	 * @param bankAccount  the bank account associated with the student
	 * @param teachers  the set of teachers assigned to the student
	 * @return the newly created Student 
	 */
	public Student addStudent(String studentName, Date studentdob, int standard, BankAccount bankAccount, Set<Teacher> teachers) throws StudentException {
		Grade grade = gradeService.getOrCreateGrade(standard);
		Student student = new Student(studentName, studentdob, grade, bankAccount, teachers);
		studentDAO.insertStudent(student);
		return student;
	}

	/**
	 * <p>Retrieves all students from the system.</p>
	 * 
	 * @return a list of all students
	 */
	public List<Student> getAllStudents() throws StudentException {
		return studentDAO.getAllStudents();
	}

	/**
	 * <p>Retrieves a student by their ID.</p>
	 * 
	 * @param studentId the ID of the student to retrieve
	 * @return the Student with the given ID
	 */
	public Student getStudent(int studentId) throws StudentException {
		return studentDAO.getStudentById(studentId);
	}

	/**
	 * <p>Removes a student by their ID.</p>
	 * 
	 * @param studentId  the ID of the student to remove
	 * @return true if the student was successfully removed, false otherwise
	 */
	public boolean removeStudentById(int studentId) throws StudentException {
		return studentDAO.deleteStudentById(studentId);
	}

	/**
	 * <p>Closes the connection to the database.</p>
	 */
	public void closeConnection() throws StudentException {
		studentDAO.shutDown();
	}

	/**
	 * <p>Checks if an account number already exists in the system.</p>
	 * 
	 * @param accountNumber  the account number to check
	 * @return true if the account number exists, false otherwise
	 */
	public boolean isAccountNumberExists(long accountNumber) throws StudentException {
		return bankAccountService.isAccountNumberExists(accountNumber);
	}

	/**
	 * Retrieves a bank account with the given details.
	 * 
	 * @param bankName  the name of the bank
	 * @param branchName  the name of the branch
	 * @param accountNumber  the account number
	 * @param ifscCode  the IFSC code
	 * @param mobileNumber  the mobile number associated with the account
	 * @return the BankAccount with the given details
	 */
	public BankAccount getBankAccount(String bankName, String branchName, long accountNumber, String ifscCode, long mobileNumber) {
		return bankAccountService.getBankAccount(bankName, branchName, accountNumber, ifscCode, mobileNumber);
	}

	/**
	 * Retrieves the set of teachers for a given standard.
	 * 
	 * @param subjectsOption  the subjects option for the standard
	 * @param groupOption   the group option for the standard
	 * @return the set of teachers for the given standard
	 */
	public Set<Teacher> getTeachersForStandard(int subjectsOption, int groupOption) throws StudentException {
		return teacherService.getTeachersForStandard(subjectsOption, groupOption);
	}
}
