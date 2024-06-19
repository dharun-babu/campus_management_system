package com.i2i.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.i2i.app.common.Subject;
import com.i2i.app.customexception.StudentException;
import com.i2i.app.model.Grade;
import com.i2i.app.model.Student;
import com.i2i.app.model.Teacher;
import com.i2i.app.service.TeacherService;
import com.i2i.app.util.StringUtil;

public class TeacherController {
	private  Scanner scanner = new Scanner(System.in);
	private TeacherService teacherService = new TeacherService();

    public void startApplication() throws StudentException {
		 boolean repetition = true;
         System.out.println("\n\t\t\tWELCOME TO TEACHER PAGE");
		 do {
			 System.out.println("\n\t\t\t\tENTER THE OPTION...\n\n1.DISPLAY ALL TEACHER \n2.DISPLAY PARTICULAR TEACHER \n3.EXIT");
             int mainMenuOption = scanner.nextInt();
			 scanner.nextLine();
			 switch (mainMenuOption) {
			 case 1 : {
			      displayAllTeachers();
				  break;
			 }
			 case 2 : {
			      displayParticularTeacher();
				  break;
			 }
			 case 3 : {
				  teacherService.closeConnection();
				  repetition = false;
				  break;
			 }
			 default : {
				  System.out.println("INVALID OPTION");
			 }
			 }
		 } while (repetition);
	}

	private void displayAllTeachers() throws StudentException {
		 System.out.println("\n\t\t\tENTER THE OPTION...\n\n1.ALONG WITH STUDENT DETAILS\n2.ALONG WITH STUDENT AND GRADE DETAILS");
		 int displayOption = scanner.nextInt();
		 int countOfPerson = 1;
		 List<Teacher> teachers = teacherService.getAllTeachers();
		 for (Teacher teacher : teachers){
			 display(displayOption, teacher, countOfPerson++);
		 }
		 scanner.nextLine();
	}

    private void displayParticularTeacher() throws StudentException {
	     System.out.println("ENTER THE SUBJECT NAME : ");
		 String subjectName = check(scanner.nextLine()).toUpperCase();
		 Subject subject  = Subject.getSubjectInstance(subjectName);
	     do {
		     if (null != subject && subject.isSubject()) {
			     break;
			 } else {
				 System.out.println("\nENTER THE CORRECT SUBJECT NAME.");
				 subjectName = check(scanner.nextLine()).toUpperCase();
			     subject = subject.getSubjectInstance(subjectName);
			 }
		 } while (true);
		 System.out.println("\n\t\t\tENTER THE OPTION...\n\n1.ALONG WITH STUDENT DETAILS\n2.ALONG WITH STUDENT AND GRADE DETAILS");
		 int displayOption = scanner.nextInt();
		 Teacher teacher = teacherService.getTeacher(subjectName);
		 display(displayOption, teacher, 0);
	}

	private void display(int displayOption, Teacher teacher, int countOfPerson) {
	     switch (displayOption) {
		 case 1 : {
			  System.out.println("\n\t\t\t TEACHER "+ countOfPerson +" DETAILS\n"+ teacher + "\n\t\t\tSTUDENT DETAILS\n");
			  int studentCount = 1;
			  for (Student student : teacher.getStudents()) {
			      System.out.println("STUDENT NUMBER "+ (studentCount++) + student);
			  }
			  break;
		 }
		 case 2 : {
			  System.out.println("\n\t\t\t TEACHER "+ countOfPerson +" DETAILS\n"+ teacher + "\n\t\t\tSTUDENT DETAILS\n");
			  int studentCount = 1;
			  for (Student student : teacher.getStudents()) {
			      System.out.println("\t\t\tSTUDENT NUMBER  "+ (studentCount++) + student + "\n\t\t\tGRADE DETAILS"+student.getGrade());
			  }
			  break;
		 }
		 }
	}

	private String check(String str) throws StudentException {
		 boolean repetition = true;
		 do { 
	         if(StringUtil.validateString(str)) {
                 break;
	         } else {
	             System.out.println("ENTER ONLY ALPHABET AND SPACE:");
	             str = scanner.nextLine();
	         }
         } while (repetition);
		 return str;
	}
}