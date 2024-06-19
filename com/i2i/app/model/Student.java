package com.i2i.app.model;

import java.lang.StringBuilder;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.i2i.app.util.DateUtil;

public class Student {
    private int studentId;
    private String studentName;
    private Date studentDob;
    private Grade grade;
	private BankAccount bankAccount;
	private Set<Teacher> teachers = new HashSet<>();
    
	public Student (){}

    public Student(String studentName, Date studentDob, Grade grade, BankAccount bankAccount, Set<Teacher> teachers) {
        this.studentName = studentName;
        this.studentDob = studentDob;
        this.grade = grade;
        this.bankAccount = bankAccount;
        this.teachers = teachers;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setStudentDob(Date studentDob) {
        this.studentDob = studentDob;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }
	
	public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }
	
	public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public Date getStudentDob() {
        return studentDob;
    }

    public Grade getGrade() {
        return grade;
    }
    
	public BankAccount getBankAccount() {
		return bankAccount;
	}

	public Set<Teacher> getTeachers() {
		return teachers;
	}

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nStudent Id: ").append(getStudentId())
                     .append("\nStudent Name: ").append(getStudentName())
                     .append("\nStudent DOB: ").append(getStudentDob())
                     .append("\nStudent Age: ").append(DateUtil.calculateYearDifference(getStudentDob()));
        return stringBuilder.toString();
    }
}
