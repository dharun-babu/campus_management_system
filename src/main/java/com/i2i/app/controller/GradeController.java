package com.i2i.app.controller;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.model.Grade;
import com.i2i.app.model.Student;
import com.i2i.app.model.Teacher;
import com.i2i.app.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * This class manages grade-related operations, handles user interactions, and delegates tasks.
 */
@Controller
public class GradeController {
    @Autowired
    private GradeService gradeService;
    private static final Logger logger = LogManager.getLogger(GradeController.class);
    private Scanner scanner = new Scanner(System.in);

    /**
     * Starts the application and displays the main menu for grade operations.
     */
    public void startApplication() {
        logger.info("GradeController started");
        try {
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
                        logger.info("Exiting GradeController");
                        repetition = false;
                        break;
                    default:
                        logger.warn("Invalid option selected: {}", mainMenuOption);
                        System.out.println("INVALID OPTION");
                        break;
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * Displays all grades with options for including additional details.
     */
    private void displayAllGrades() throws StudentException {
        logger.info("Displaying all grades");
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
     * Displays details of a particular grade based on standard and section.
     */
    private void displayParticularGrade() throws StudentException {
        logger.info("Displaying particular grade");
        System.out.println("ENTER THE STANDARD: ");
        int standard = scanner.nextInt();
        System.out.println("ENTER THE SECTION: ");
        char section = scanner.next().charAt(0);
        System.out.println("\n\t\t\tENTER THE OPTION...\n\n1. ALONG WITH STUDENT DETAILS\n2. ALONG WITH STUDENT'S TEACHER DETAILS");
        int displayOption = scanner.nextInt();
        logger.info("Requested grade details for Standard: {}, Section: {}, Display Option: {}", standard, section, displayOption);
        Grade grade = gradeService.getGrade(standard, section);
        if (grade != null) {
            logger.info("Successfully retrieved grade details for Standard: {}, Section: {}", standard, section);
            displayGradeDetails(displayOption, grade, 0);
        } else {
            logger.warn("No grade found for Standard: {}, Section: {}", standard, section);
            System.out.println("No grade found for the specified standard and section.");
        }
    }

    /**
     * Displays grade details based on the chosen display option.
     *
     * @param displayOption  the display option chosen by the user.
     * @param grade          the grade whose details are to be displayed.
     * @param countOfGrade   the count to label the grade details.
     */
    private void displayGradeDetails(int displayOption, Grade grade, int countOfGrade) {
        logger.info("Displaying the details of grade : {}", grade);
        switch (displayOption) {
            case 1:
                displayGradeWithStudentDetails(grade, countOfGrade);
                break;
            case 2:
                displayGradeWithStudentAndTeacherDetails(grade, countOfGrade);
                break;
            default:
                logger.warn("Invalid display option selected: {}", displayOption);
                System.out.println("INVALID OPTION");
        }
    }

    /**
     * Displays grade details along with student details.
     *
     * @param grade         the grade whose details are to be displayed.
     * @param countOfGrade  the count to label the grade details.
     */
    private void displayGradeWithStudentDetails(Grade grade, int countOfGrade) {
        logger.info("Displaying the student details for grade: {}", grade);
        System.out.println("\n\t\t\tGRADE " + countOfGrade + " DETAILS\n" + grade + "\n\t\t\tSTUDENT DETAILS\n");
        int studentCount = 1;
        for (Student student : grade.getStudents()) {
            System.out.println("\nSTUDENT NUMBER: " + studentCount++ + "\n" + student);
        }
    }

    /**
     * Displays grade details along with student and teacher details.
     *
     * @param grade         the grade whose details are to be displayed.
     * @param countOfGrade  the count to label the grade details.
     */
    private void displayGradeWithStudentAndTeacherDetails(Grade grade, int countOfGrade) {
        logger.info("Displaying grade with student and teacher details for grade: {}", grade);
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
