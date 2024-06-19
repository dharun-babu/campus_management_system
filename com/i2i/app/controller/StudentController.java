package com.i2i.app.controller;

import java.util.Date;
import java.util.Scanner;
import java.util.Set;

import com.i2i.app.common.Group;
import com.i2i.app.customexception.StudentException;
import com.i2i.app.model.BankAccount;
import com.i2i.app.model.Student;
import com.i2i.app.model.Teacher;
import com.i2i.app.service.StudentService;
import com.i2i.app.util.DateUtil;
import com.i2i.app.util.StringUtil;

/**
 * This class to manage student-related operations and handles user interactions and delegates tasks.
 */
public class StudentController {

    private Scanner scanner = new Scanner(System.in);
    private StudentService studentService = new StudentService();

    /**
     * <p> Starts the application and displays the main menu for student operations.
	 * The user can choose to add students, display all students, display a particular student, remove a student, or exit.<p>
     */
    public void startApplication() throws StudentException {
        boolean repetition = true;
        System.out.println("\n\t\t\tWELCOME TO STUDENT PAGE");
        while (repetition) {
            System.out.println("\n\t\t\t\tENTER THE OPTION...\n\n1.ADD STUDENT \n2.DISPLAY ALL \n3.DISPLAY PARTICULAR \n4.REMOVE \n5.EXIT");
            int mainMenuOption = scanner.nextInt();
            scanner.nextLine();

            switch (mainMenuOption) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    displayAllStudents();
                    break;
                case 3:
                    displayParticularStudent();
                    break;
                case 4:
                    removeStudent();
                    break;
                case 5:
                    studentService.closeConnection();
                    repetition = false;
                    break;
                default:
                    System.out.println("INVALID OPTION");
            }
        }
    }

    /**
     * <p> Adds a new student to the system.
	 * Prompts the user to enter details for each student including name, date of birth, standard, and bank account details.</p>
     */
    private void addStudent() throws StudentException {
        System.out.println("HOW MANY STUDENTS TO ADD: ");
        int addCount = scanner.nextInt();
        scanner.nextLine();
        while (addCount > 0) {
            System.out.println("ENTER THE NAME:");
            String name = validateInput(scanner.nextLine());
            System.out.println("ENTER THE DOB IN FORMAT OF (YYYY-MM-DD):");
            Date dob = DateUtil.checkAndFormatDate(scanner.nextLine());
            System.out.println("ENTER THE STANDARD IN RANGE OF 1 - 12:");
            int standard = scanner.nextInt();
            scanner.nextLine();
            BankAccount bankAccount = getBankAccountDetails();
            Set<Teacher> teachers = getTeacherForStandard(standard);
            Student student = studentService.addStudent(name, dob, standard, bankAccount, teachers);
            System.out.println("\n\t\t\tYOUR ID: " + student.getStudentId());
            addCount--;
        }
    }

    /**
     * <p> Retrieves bank account details from the user.
	 * Prompts the user to enter details including account number, bank name, branch name, IFSC code, and mobile number.
     * Ensures the account number is unique.</p>
     *
     * @return BankAccount The provided bank account details.
     */
    private BankAccount getBankAccountDetails() throws StudentException {
        System.out.println("\n\t\t\tENTER BANK DETAILS..");
        System.out.println("ENTER THE ACCOUNT NUMBER IN RANGE OF 1 - 16: ");
        long accountNumber = scanner.nextLong();
        while (!studentService.isAccountNumberExists(accountNumber)) {
            System.out.println("\t\t\tACCOUNT NUMBER ALREADY EXISTS.\nENTER ANOTHER ACCOUNT NUMBER: ");
            accountNumber = scanner.nextLong();
        }
        scanner.nextLine();
        System.out.println("ENTER THE BANK NAME: ");
        String bankName = validateInput(scanner.nextLine());
        System.out.println("ENTER THE BRANCH NAME: ");
        String branchName = validateInput(scanner.nextLine());
        System.out.println("ENTER THE IFSC CODE: ");
        String ifscCode = scanner.nextLine();
        System.out.println("ENTER THE MOBILE NUMBER: ");
        long mobileNumber = scanner.nextLong();
        scanner.nextLine()
        return studentService.getBankAccount(bankName, branchName, accountNumber, ifscCode, mobileNumber);
    }

    /**
     * <p> Retrieves teachers for the given standard.
	 * Based on the standard, the appropriate set of teachers is obtained.
     * For standards above 10, the user is prompted to enter a group name (e.g., COMPUTER SCIENCE, BIOLOGY, COMMERCE).</p>
     *
     * @param standard    the standard for which teachers are to be retrieved.
     * @return a Set of Teachers.
     */
    private Set<Teacher> getTeacherForStandard(int standard) throws StudentException {
        int subjectsOption = (standard <= 5) ? 1 : (standard <= 10) ? 2 : 3;
        if (subjectsOption == 3) {
            System.out.println("\nENTER THE GROUP NAME.\n1.COMPUTER SCIENCE\n2.BIOLOGY\n3.COMMERCE");
            String groupName = validateInput(scanner.nextLine()).toUpperCase();
            Group group = Group.getGroupInstance(groupName);
            while (group == null || group.getGroupId() == 0) {
                System.out.println("\nENTER THE CORRECT GROUP NAME.\n1.COMPUTER SCIENCE\n2.BIOLOGY\n3.COMMERCE");
                groupName = validateInput(scanner.nextLine()).toUpperCase();
                group = Group.getGroupInstance(groupName);
            }
            return studentService.getTeachersForStandard(subjectsOption, group.getGroupId());
        }
        return studentService.getTeachersForStandard(subjectsOption, 0);
    }

    /**
     * <p> Displays all students with options for including additional details.
	 * The user can choose to display grade details, bank details, subject and teacher details, or all information.</p>
     */
    private void displayAllStudents() throws StudentException {
        System.out.println("\n\t\t\tENTER THE OPTION...\n\n1.ALONG WITH GRADE DETAILS\n2.ALONG WITH BANK DETAILS\n3.ALONG WITH SUBJECT AND TEACHER DETAILS\n4.ALL INFORMATION");
        int displayOption = scanner.nextInt();
        int countOfPerson = 1;
        for (Student student : studentService.getAllStudents()) {
            displayStudentDetails(displayOption, student, countOfPerson++);
        }
    }

    /**
     * <p> Displays details of a particular student based on student ID.
	 * The options include displaying grade details, bank details, subject and teacher details, or all information.</p>
     */
    private void displayParticularStudent() throws StudentException {
        System.out.println("\n\t\t\tENTER THE OPTION...\n\n1.ALONG WITH GRADE DETAILS\n2.ALONG WITH BANK DETAILS\n3.ALONG WITH SUBJECT AND TEACHER DETAILS\n4.ALL INFORMATION");
        int displayOption = scanner.nextInt();
        System.out.println("\nENTER THE STUDENT ID: ");
        int studentId = scanner.nextInt();
        Student student;
        while ((student = studentService.getStudent(studentId)) == null) {
            System.out.println("ENTER THE CORRECT STUDENT ID: ");
            studentId = scanner.nextInt();
        }
        displayStudentDetails(displayOption, student, 1);
    }

    /**
     * <p> Displays student details based on the chosen display option.
	 *  Prompts the user to enter the student ID and confirms the removal.</p>
     *
     * @param displayOption  the display option chosen by the user.
     * @param student        the student whose details are to be displayed.
     * @param countOfPerson  the count to label the student details.
     */
    private void displayStudentDetails(int displayOption, Student student, int countOfPerson) {
        switch (displayOption) {
            case 1:
                System.out.println("\n\t\t\t" + countOfPerson + " STUDENT DETAILS\n" + student + "\n\t\t\tGRADE DETAILS\n" + student.getGrade());
                break;
            case 2:
                System.out.println("\n\t\t\tSTUDENT " + countOfPerson + " DETAILS\n" + student + "\n\t\t\tGRADE DETAILS\n" + student.getGrade() + "\n\t\t\tBANK ACCOUNT DETAILS\n" + student.getBankAccount());
                break;
            case 3:
                System.out.println("\n\t\t\tSTUDENT " + countOfPerson + " DETAILS\n" + student + "\n GRADE DETAILS\n" + student.getGrade() + "\n\t\t\tSUBJECT AND TEACHER DETAILS\n");
                for (Teacher teacher : student.getTeachers()) {
                    System.out.println("\n\t\t\tSUBJECT NAME: " + teacher.getSubject() + "\n\n\t\t\tSUBJECT TEACHER DETAILS\n" + teacher);
                }
                break;
            case 4:
                System.out.println("\n\t\t\tSTUDENT " + countOfPerson + " DETAILS\n" + student + "\n GRADE DETAILS\n" + student.getGrade() + "\n\t\t\tBANK ACCOUNT DETAILS\n" + student.getBankAccount() + "\n\t\t\tSUBJECT AND TEACHER DETAILS\n");
                for (Teacher teacher : student.getTeachers()) {
                    System.out.println("\n\t\t\tSUBJECT NAME: " + teacher.getSubject() + "\n\n\t\t\tSUBJECT TEACHER DETAILS\n" + teacher);
                }
                break;
            default:
                System.out.println("INVALID OPTION");
        }
    }

    /**
     * <p> Removes a student based on student ID.
	 *  Prompts the user to enter the student ID and confirms the removal.</p>
     */
    private void removeStudent() throws StudentException {
        System.out.println("ENTER THE STUDENT ID: ");
        int studentId = scanner.nextInt();
        while (!studentService.removeStudentById(studentId)) {
            System.out.println("ENTER THE STUDENT ID: ");
            studentId = scanner.nextInt();
        }
        System.out.println("REMOVE STUDENT SUCCESSFUL!");
    }

    /**
     * <p> Validates the input string to ensure it contains only alphabets and spaces.
	 * Prompts the user to re-enter the input if validation fails.</p>
     *
     * @param input the input string to be validated.
     * @return the validated input string.
     */
    private String validateInput(String input) throws StudentException {
        while (!StringUtil.validateString(input)) {
            System.out.println("ENTER ONLY ALPHABET AND SPACE:");
            input = scanner.nextLine();
        }
        return input;
    }
}
