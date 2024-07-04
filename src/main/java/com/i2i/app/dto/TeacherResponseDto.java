package com.i2i.app.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class TeacherResponseDto {
    private int teacherId;
    private String teacherName;
    private String subject;
    private Date dateOfJoin;
    private Set<StudentMapDto> students = new HashSet<>();

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Date getDateOfJoin() {
        return dateOfJoin;
    }

    public void setDateOfJoin(Date dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }

    public Set<StudentMapDto> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentMapDto> students) {
        this.students = students;
    }
}
