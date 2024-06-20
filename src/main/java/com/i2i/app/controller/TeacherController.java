package com.i2i.app.controller;

import java.util.List;
import java.util.Scanner;

import com.i2i.app.common.Subject;
import com.i2i.app.customexception.StudentException;
import com.i2i.app.model.Student;
import com.i2i.app.model.Teacher;
import com.i2i.app.service.TeacherService;
import com.i2i.app.util.StringUtil;

/**
 * This class is responsible for handling user interactions, assigning tasks, and managing operations related to teachers.
 */
public class TeacherController {

    private Scanner scanner = new Scanner(System.in);
    private TeacherService teacherService = new TeacherService();

    /**
     * <p> Starts the application and displays the main menu for teacher operations.</p>
     *
     */
    public void startApplication() throws StudentException {
        boolean repetition = true;
        System.out.println("\n\t\t\tWELCOME TO TEACHER PAGE");
        while (repetition) {
            System.out.println("\n\t\t\t\tENTER THE OPTION...\n\n1.DISPLAY ALL TEACHERS \n2.DISPLAY PARTICULAR TEACHER \n3.EXIT");
            int mainMenuOption = scanner.nextInt();
            scanner.nextLine();
            switch (mainMenuOption) {
                case 1:
                    displayAllTeachers();
                    break;
                case 2:
                    displayParticularTeacher();
                    break;
                case 3:
                    repetition = false;
				    break;
                default:
                    System.out.println("INVALID OPTION");
				    break;
            }
        }
    }

    /**
     * <p> Displays all teachers with options to include student and grade details.</p>
     */
    private void displayAllTeachers() throws StudentException {
        System.out.println("\n\t\t\tENTER THE OPTION...\n\n1.ALONG WITH STUDENT DETAILS\n2.ALONG WITH STUDENT AND GRADE DETAILS");
        int displayOption = scanner.nextInt();
        int countOfPerson = 1;
        List<Teacher> teachers = teacherService.getAllTeachers();
        for (Teacher teacher : teachers) {
            displayTeacherDetails(displayOption, teacher, countOfPerson++);
        }
        scanner.nextLine();
    }

    /**
     * <p> Displays a particular teacher based on the subject name with options to include student and grade details.</p>
     */
    private void displayParticularTeacher() throws StudentException {
        System.out.println("ENTER THE SUBJECT NAME : ");
        String subjectName = validateInput(scanner.nextLine()).toUpperCase();
        Subject subject = Subject.getSubjectInstance(subjectName);
        while (subject == null || !subject.isSubject()) {
            System.out.println("\nENTER THE CORRECT SUBJECT NAME.");
            subjectName = validateInput(scanner.nextLine()).toUpperCase();
            subject = Subject.getSubjectInstance(subjectName);
        }
        System.out.println("\n\t\t\tENTER THE OPTION...\n\n1.ALONG WITH STUDENT DETAILS\n2.ALONG WITH STUDENT AND GRADE DETAILS");
        int displayOption = scanner.nextInt();
        Teacher teacher = teacherService.getTeacher(subject.getDisplayName());
        displayTeacherDetails(displayOption, teacher, 0);
    }

    /**
     * <p> Displays the details of a teacher based on the chosen display option.</p>
     *
     * @param displayOption  the display option chosen by the user.
     * @param teacher        the teacher whose details are to be displayed.
     * @param countOfPerson  the count to label the teacher details.
     */
    private void displayTeacherDetails(int displayOption, Teacher teacher, int countOfPerson) {
        switch (displayOption) {
            case 1:
                System.out.println("\n\t\t\t TEACHER " + countOfPerson + " DETAILS\n" + teacher + "\n\t\t\tSTUDENT DETAILS\n");
                int studentCount = 1;
                for (Student student : teacher.getStudents()) {
                    System.out.println("STUDENT NUMBER " + (studentCount++) + " " + student);
                }
                break;
            case 2:
                System.out.println("\n\t\t\t TEACHER " + countOfPerson + " DETAILS\n" + teacher + "\n\t\t\tSTUDENT DETAILS\n");
                studentCount = 1;
                for (Student student : teacher.getStudents()) {
                    System.out.println("\t\t\tSTUDENT NUMBER  " + (studentCount++) + " " + student + "\n\t\t\tGRADE DETAILS " + student.getGrade());
                }
                break;
            default:
                System.out.println("INVALID OPTION");
        }
    }

    /**
     * <p> Validates the input string to ensure it contains only alphabets and spaces.</p>
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
