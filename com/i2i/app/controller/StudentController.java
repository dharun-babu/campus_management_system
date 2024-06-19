package com.i2i.app.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import com.i2i.app.common.Group;
import com.i2i.app.customexception.StudentException;
import com.i2i.app.model.BankAccount;
import com.i2i.app.model.Grade;
import com.i2i.app.model.Student;
import com.i2i.app.model.Teacher;
import com.i2i.app.service.BankAccountService;
import com.i2i.app.service.GradeService;
import com.i2i.app.service.StudentService;
import com.i2i.app.service.TeacherService;
import com.i2i.app.util.DateUtil;
import com.i2i.app.util.StringUtil;

public class StudentController {
	private  Scanner scanner = new Scanner(System.in);
	private StudentService studentService = new StudentService();

    public void startApplication() throws StudentException {
		 boolean repetition = true;
         System.out.println("\n\t\t\t WELCOME TO STUDENT PAGE");
		 do {
			 System.out.println("\n\t\t\t\tENTER THE OPTION...\n\n1.ADD STUDENT \n2.DISPLAY ALL \n3.DISPLAY PARTICULAR \n4.REMOVE \n5.EXIT");
             int mainMenuOption = scanner.nextInt();
			 scanner.nextLine();
			 switch (mainMenuOption) {
			 case 1 : {
			      addStudent();
				  break;
			 }
			 case 2 : {
			      displayAllStudents();
				  break;
			 }
			 case 3 : {
			      displayParticularStudent();
				  break;
			 }
			 case 4: {
			      removeStudent();
				  break;
			 }
			 case 5 : {
				  studentService.closeConnection();
				  repetition = false;
				  break;
			 }
			 default : {
				  System.out.println("INVALID OPTION");
			 }
			 }
		 } while (repetition);
	}

	private void addStudent() throws StudentException {
		 System.out.println(" HOW MANY STUDENT TO ADD : ");
		 int addCount = scanner.nextInt();
		 scanner.nextLine();
		 while (0 < addCount) {
		     System.out.println("ENTER THE NAME :");
		     String name = check(scanner.nextLine());
		     System.out.println("ENTER THE DOB IN FORMAT OF (YYYY-MM-DD) :");
		     Date dob = DateUtil.checkAndFormatDate(scanner.nextLine());
		     System.out.println("ENTER THE STANDARD IN RANGE OF 1 - 12 :");
		     int standard = scanner.nextInt();
			 BankAccount bankAccount = getBankAccountDetails();
			 Set<Teacher> teachers = getTeacherForStandard(standard);
			 do {
				 if (null != teachers) {
					 break;
				 } else {
					 teachers = getTeacherForStandard(standard);
				 }
			 } while (true);
		     Student student = studentService.addStudent(name, dob, standard, bankAccount, teachers);
		     System.out.println("\n\t\t\tYOUR ID : "+student.getStudentId());
			 addCount--;
		 }
	}
    
	private BankAccount getBankAccountDetails() throws StudentException {
		 System.out.println("\n\t\t\tENTER BANK DETAILS..");
		 System.out.println("ENTER THE ACCOUNT NUMBER IN RANGE OF 1 - 16 : ");
		 long accountNumber = scanner.nextLong();
		 do {
			 if (studentService.isAccountNumberExists(accountNumber)) {
				 break;
			 } else {
				 System.out.println("\t\t\tACCOUNT NUMBER ALREADY EXIST.\nENTER THE ANOTHER ACCOUNT NUMBER: ");
		         accountNumber = scanner.nextLong();
			 }
		 } while (true);
		 scanner.nextLine();
		 System.out.println("ENTER THE BANK NAME : ");
		 String bankName = check(scanner.nextLine());
		 System.out.println("ENTER THE BRANCH NAME : ");
		 String branchName = check(scanner.nextLine());
		 System.out.println("ENTER THE IFSC CODE : ");
		 String ifscCode = scanner.nextLine();
		 System.out.println("ENTER THE MOBILE NUMBER : ");
		 long mobileNumber = scanner.nextLong();
		 scanner.nextLine();
		 return studentService.getBankAccount(bankName, branchName, accountNumber, ifscCode, mobileNumber);
	}

	private Set<Teacher> getTeacherForStandard(int standard) throws StudentException {
		 int subjectsOption = (1 <= standard && 5 >= standard ) ? 1 : (5 < standard && 10 >= standard ) ? 2 : 3;
		 switch (subjectsOption) {
		 case 1 : {
			  return studentService.getTeachersForStandard(subjectsOption, 0);
		 }
		 case 2 : {
			  return studentService.getTeachersForStandard(subjectsOption, 0);
		 }
		 case 3 : {
			  System.out.println("\nENTER THE GROUP NAME.\n1.COMPUTER SCIENCE\n2.BIOLOGY\n3.COMMERCE");
			  String groupName = check(scanner.nextLine()).toUpperCase();
			  Group group = Group.getGroupInstance(groupName);
			  int groupOption = 0;
			  do {
				  if (null != group && 0 != group.getGroupId()) {
					  groupOption = group.getGroupId();
					  break;
				  } else {
					  System.out.println("\nENTER THE CORRECR GROUP NAME.\n1.COMPUTER SCIENCE\n2.BIOLOGY\n3.COMMERCE");
					  groupName = check(scanner.nextLine()).toUpperCase();
			          group = Group.getGroupInstance(groupName);
				  }
			  } while (true);
			  return studentService.getTeachersForStandard(subjectsOption, groupOption);
		 }
		 }
		 return null;
	}

	private void displayAllStudents() throws StudentException {
		 System.out.println("\n\t\t\tENTER THE OPTION...\n\n1.ALONG WITH GRADE DETAILS\n2.ALONG WITH BANK DETAILS\n3.ALONG WITH SUBJECT AND TEACHER DETAILS\n4.ALL INFORMATION");
		 int displayOption = scanner.nextInt();
		 int countOfPerson = 1;
		 for (Student student : studentService.getAllStudents()) {
		     display(displayOption, student, countOfPerson++);
		 }
	}

	private void displayParticularStudent() throws StudentException {
		 System.out.println("\n\t\t\tENTER THE OPTION...\n\n1.ALONG WITH GRADE DETAILS\n2.ALONG WITH BANK DETAILS\n3.ALONG WITH SUBJECT AND TEACHER DETAILS\n4.ALL INFORMATION");
		 int displayOption = scanner.nextInt();
		 System.out.println("\nENTER THE STUDENT ID : ");
		 int studentId = scanner.nextInt();
		 do {
			 Student student = studentService.getStudent(studentId);
			 if (null != student) {
				 display(displayOption, student, 1);
			     break;
			 } else {
				 System.out.println("ENTER THE CORRECT STUDENT ID : ");
				 studentId = scanner.nextInt();
			 }
		 } while (true);
	}

	private void display(int displayOption, Student student, int countOfPerson) {
	     switch (displayOption) {
		 case 1 : {
			  System.out.println("\n\t\t\t"+ countOfPerson +" STUDENT DETAILS\n"+ student + "\n\t\t\tGRADE DETAILS\n"+student.getGrade());
			  break;
		 }
		 case 2 : {
			  System.out.println("\n\t\t\t STUDENT "+ countOfPerson +"DETAILS\n"+ student + "\n\t\t\tGRADE DETAILS\n"+student.getGrade()+ "\n\t\t\tBANK ACCOUNT DETAILS \n" +student.getBankAccount());
			  break;
		 }
		 case 3 : {
			  System.out.println("\n\t\t\t STUDENT "+ countOfPerson +"DETAILS\n"+ student + "\n GRADE DETAILS\n"+student.getGrade()+ "\n\t\t\tSUBJECT AND TEACHER DETAILS \n");
			  for (Teacher teacher : student.getTeachers()) {
			      System.out.println("\n\t\t\t SUBJECT NAME : "+ teacher.getSubject() +"\n\n\t\t\t SUBJECT TEACHER DETAILS\n"+teacher);
			  }
			  break;
		 }
		 case 4 : {
			  System.out.println("\n\t\t\t STUDENT "+ countOfPerson +"DETAILS\n"+ student + "\n GRADE DETAILS\n"+student.getGrade()+ "\n\t\t\tBANK ACCOUNT DETAILS \n" +student.getBankAccount() +"\n\t\t\tSUBJECT AND TEACHER DETAILS \n");
			  for (Teacher teacher : student.getTeachers()) {
			      System.out.println("\n\t\t\t SUBJECT NAME : "+ teacher.getSubject() +"\n\n\t\t\t SUBJECT TEACHER DETAILS\n"+teacher);
			  }
			  break;
		 }
		 }
	}

	private void removeStudent () throws StudentException {
		 boolean repetition = true;
		 System.out.println("ENTER THE STUDENT ID : ");
		 int studentId = scanner.nextInt();
		 do {
			 if (studentService.removeStudentById(studentId)) {
				 System.out.println("REMOVE STUDENT SUCCESSFULL !");
				 break;
			 } else {
				 System.out.println("ENTER THE STUDENT ID : ");
		         studentId = scanner.nextInt();
			 }
		 } while (repetition);
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