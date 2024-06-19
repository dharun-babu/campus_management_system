package com.i2i.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.i2i.app.common.Subject;
import com.i2i.app.customexception.StudentException;
import com.i2i.app.model.Grade;
import com.i2i.app.model.Student;
import com.i2i.app.model.Teacher;
import com.i2i.app.service.GradeService;

public class GradeController {
	private  Scanner scanner = new Scanner(System.in);
	private GradeService gradeService = new GradeService();

    public void startApplication() throws StudentException {
		 boolean repetition = true;
         System.out.println("\n\t\t\tWELCOME TO GRADE PAGE");
		 do {
			 System.out.println("\n\t\t\t\tENTER THE OPTION...\n\n1.DISPLAY ALL GRADE \n2.DISPLAY PARTICULAR GRADE \n3.EXIT");
             int mainMenuOption = scanner.nextInt();
			 scanner.nextLine();
			 switch (mainMenuOption) {
			 case 1 : {
			      displayAllGrades();
				  break;
			 }
			 case 2 : {
			      displayParticularGrade();
				  break;
			 }
			 case 3 : {
				  gradeService.closeConnection();
				  repetition = false;
				  break;
			 }
			 default : {
				  System.out.println("INVALID OPTION");
			 }
			 }
		 } while (repetition);
	}

	private void displayAllGrades() throws StudentException {
		 System.out.println("\n\t\t\tENTER THE OPTION...\n\n1.ALONG WITH STUDENT DETAILS\n2.ALONG WITH STUDENT'S TEACHER DETAILS");
		 int displayOption = scanner.nextInt();
		 int countOfGrade = 1;
		 List<Grade> grades = gradeService.getAllGrades();
		 for (Grade grade : grades){
			 display(displayOption, grade, countOfGrade++);
		 }
		 scanner.nextLine();
	}
    
	private void displayParticularGrade() throws StudentException {
	     System.out.println("ENTER THE STANDARD : ");
		 int standard = scanner.nextInt();
		 System.out.println("ENTER THE SECTION : ");
		 char section = scanner.next().charAt(0);
		 System.out.println("\n\t\t\tENTER THE OPTION...\n\n1.ALONG WITH STUDENT DETAILS\n2.ALONG WITH STUDENT'S TEACHER DETAILS");
		 int displayOption = scanner.nextInt();
		 Grade grade = gradeService.getGrade(standard, section);
		 display(displayOption, grade, 0);
	}

	private void display(int displayOption, Grade grade, int countOfGrade) {
	     switch (displayOption) {
		 case 1 : {
			  System.out.println("\n\t\t\t GARDE "+ countOfGrade +" DETAILS\n"+ grade + "\n\t\t\tSTUDENT DETAILS\n");
			  int studentCount = 1;
			  for (Student student : grade.getStudents()) {
			      System.out.println("\nSTUDENT NUMBER : "+ (studentCount++) + student);
			  }
			  break;
		 }
		 case 2 : {
			  System.out.println("\n\t\t\t GRADE "+ countOfGrade +" DETAILS\n"+ grade + "\n\t\t\tSTUDENT DETAILS\n");
			  int studentCount = 1;
			  for (Student student : grade.getStudents()) {
			      System.out.println("\t\t\tSTUDENT NUMBER : "+ (studentCount++) + student + "\n\t\t\tTEACHER DETAILS");
				  int subjectTeacherCount = 1;
				  for (Teacher teacher : student.getTeachers()) {
			          System.out.println("\nTEACHER NUMBER : "+ (subjectTeacherCount++) + teacher);
			      }
			  }
			  break;
		 }
		 }
	}
}