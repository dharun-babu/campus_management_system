package com.i2i.app.controller;

import java.util.List;
import java.util.Scanner;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.model.Grade;
import com.i2i.app.model.Student;
import com.i2i.app.model.Teacher;
import com.i2i.app.service.GradeService;

/**
 * This class to manage grade-related operations and handles user interactions and delegates tasks.
 */
public class GradeController {

    private Scanner scanner = new Scanner(System.in);
    private GradeService gradeService = new GradeService();

    /**
     * <p> Starts the application and displays the main menu for grade operations.</p>
     */
    public void startApplication() throws StudentException {
        boolean repetition = true;
        System.out.println("\n\t\t\tWELCOME TO GRADE PAGE");
        while (repetition) {
            System.out.println("\n\t\t\t\tENTER THE OPTION...\n\n1. DISPLAY ALL GRADES \n2. DISPLAY PARTICULAR GRADE \n3. EXIT");
            int mainMenuOption = scanner.nextInt();
            scanner.nextLine();
            switch (mainMenuOption) {
                case 1:
                    displayAllGrades();
                    break;
                case 2:
                    displayParticularGrade();
                    break;
                case 3:
                    gradeService.closeConnection();
                    repetition = false;
                    break;
                default:
                    System.out.println("INVALID OPTION");
            }
        }
    }

    /**
     * <p> Displays all grades with options for including additional details.</p>
     */
    private void displayAllGrades() throws StudentException {
        System.out.println("\n\t\t\tENTER THE OPTION...\n\n1. ALONG WITH STUDENT DETAILS\n2. ALONG WITH STUDENT'S TEACHER DETAILS");
        int displayOption = scanner.nextInt();
        int countOfGrade = 1;
        List<Grade> grades = gradeService.getAllGrades();
        for (Grade grade : grades) {
            displayGradeDetails(displayOption, grade, countOfGrade++);
        }
        scanner.nextLine();
    }

    /**
     * <p> Displays details of a particular grade based on standard and section.</p>
     */
    private void displayParticularGrade() throws StudentException {
        System.out.println("ENTER THE STANDARD: ");
        int standard = scanner.nextInt();
        System.out.println("ENTER THE SECTION: ");
        char section = scanner.next().charAt(0);
        System.out.println("\n\t\t\tENTER THE OPTION...\n\n1. ALONG WITH STUDENT DETAILS\n2. ALONG WITH STUDENT'S TEACHER DETAILS");
        int displayOption = scanner.nextInt();
        Grade grade = gradeService.getGrade(standard, section);
        displayGradeDetails(displayOption, grade, 0);
    }

    /**
     * Displays grade details based on the chosen display option.
     *
     * @param displayOption  the display option chosen by the user.
     * @param grade          the grade whose details are to be displayed.
     * @param countOfGrade   the count to label the grade details.
     */
    private void displayGradeDetails(int displayOption, Grade grade, int countOfGrade) {
        switch (displayOption) {
            case 1:
                displayGradeWithStudentDetails(grade, countOfGrade);
                break;
            case 2:
                displayGradeWithStudentAndTeacherDetails(grade, countOfGrade);
                break;
            default:
                System.out.println("INVALID OPTION");
        }
    }

    /**
     * <p> Displays grade details along with student details.</p>
     *
     * @param grade         the grade whose details are to be displayed.
     * @param countOfGrade  the count to label the grade details.
     */
    private void displayGradeWithStudentDetails(Grade grade, int countOfGrade) {
        System.out.println("\n\t\t\tGRADE " + countOfGrade + " DETAILS\n" + grade + "\n\t\t\tSTUDENT DETAILS\n");
        int studentCount = 1;
        for (Student student : grade.getStudents()) {
            System.out.println("\nSTUDENT NUMBER: " + studentCount++ + "\n" + student);
        }
    }

    /**
     * <p> Displays grade details along with student and teacher details.</p>
     *
     * @param grade         the grade whose details are to be displayed.
     * @param countOfGrade  the count to label the grade details.
     */
    private void displayGradeWithStudentAndTeacherDetails(Grade grade, int countOfGrade) {
        System.out.println("\n\t\t\tGRADE " + countOfGrade + " DETAILS\n" + grade + "\n\t\t\tSTUDENT DETAILS\n");
        int studentCount = 1;
        for (Student student : grade.getStudents()) {
            System.out.println("\t\t\tSTUDENT NUMBER: " + studentCount++ + "\n" + student + "\n\t\t\tTEACHER DETAILS");
            int subjectTeacherCount = 1;
            for (Teacher teacher : student.getTeachers()) {
                System.out.println("\nTEACHER NUMBER: " + subjectTeacherCount++ + "\n" + teacher);
            }
        }
    }
}
