package com.i2i.app.dto;

import java.util.Date;
import java.util.List;

public class StudentResponseDto {
    private int studentId;
    private String studentName;
    private Date studentDob;
    private int age;
    private GradeMapDto gradeMapDto;
    private BankAccountMapDto bankAccountMapDto;
    private List<TeacherMapDto> teachers;

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

    public GradeMapDto getGradeMapDto() {
        return gradeMapDto;
    }

    public void setGradeMapDto(GradeMapDto gradeMapDto) {
        this.gradeMapDto = gradeMapDto;
    }

    public BankAccountMapDto getBankAccountMapDto() {
        return bankAccountMapDto;
    }

    public void setBankAccountMapDto(BankAccountMapDto bankAccountMapDto) {
        this.bankAccountMapDto = bankAccountMapDto;
    }

    public List<TeacherMapDto> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<TeacherMapDto> teachers) {
        this.teachers = teachers;
    }
}
