package com.i2i.app.model;

import java.lang.StringBuilder;
import java.util.HashSet;
import java.util.Set;

public class Grade {
    private int gradeId;
    private int standard;
    private char section;
    private Set<Student> students = new HashSet<>();
    
	public Grade() {}

	public Grade(int standard, char section) {
        this.standard = standard;
        this.section = section;
    }

    private void setGradeId(int gradeId) {
        this.gradeId = gradeId;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public void setSection(char section) {
        this.section = section;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    public int getGradeId() {
        return gradeId;
    }

    public int getStandard() {
        return standard;
    }

    public char getSection() {
        return section;
    }

    public Set<Student> getStudents() {
        return students;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nStandard : ").append(getStandard())
                     .append("\nSection: ").append(getSection());
        return stringBuilder.toString();
    }
}
