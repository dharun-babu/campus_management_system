package com.i2i.app.dto;

import java.util.Date;
import java.util.List;

public class StudentResponseDto {
    private String rollNumber;
    private String studentName;
    private Date studentDob;
    private int age;
    private GradeMapDto grade;
    private BankAccountMapDto bankAccount;
    private List<TeacherMapDto> teachers;

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Date getStudentDob() {
        return studentDob;
    }

    public void setStudentDob(Date studentDob) {
        this.studentDob = studentDob;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public GradeMapDto getGrade() {
        return grade;
    }

    public void setGrade(GradeMapDto grade) {
        this.grade = grade;
    }

    public BankAccountMapDto getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountMapDto bankAccount) {
        this.bankAccount = bankAccount;
    }

    public List<TeacherMapDto> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherMapDto> teachers) {
        this.teachers = teachers;
    }
}
