package com.i2i.app.model;

import java.lang.StringBuilder;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.i2i.app.util.DateUtil;

public class Teacher {
    private int teacherId;
    private String teacherName;
    private String subject;
    private Date dateOfJoin;
   	private Set<Student> students = new HashSet<>();

	public Teacher(){}

    public Teacher(String teacherName, String subject, Date dateOfJoin) {
		this.teacherName = teacherName;
		this.dateOfJoin = dateOfJoin;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }
	
	public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setDateOfJoin(Date dateOfJoin) {
        this.dateOfJoin = dateOfJoin;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }
	
    public int getTeacherId() {
        return teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }
	
	public String getSubject() {
        return subject;
    }

    public Date getDateOfJoin() {
        return dateOfJoin;
    }

    public Set<Student> getStudents() {
        return students;
    }
    
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nTeacher Name: ").append(getTeacherName())
                     .append("\nExperience : ").append(DateUtil.calculateYearDifference(getDateOfJoin()));
        return stringBuilder.toString();
    }
}
