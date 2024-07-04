package com.i2i.app.model;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.*;

import com.i2i.app.util.DateUtil;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private int studentId;

    @Column(name = "student_name", length = 30, nullable = false)
    private String studentName;

    @Temporal(TemporalType.DATE)
    @Column(name = "student_dob")
    private Date studentDob;

    @OneToOne
    @JoinColumn(name = "account_id")
    private BankAccount bankAccount;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "grade_id", nullable = false)
    private Grade grade;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "teachers_students",
            joinColumns = @JoinColumn(name = "student_id", referencedColumnName = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id", referencedColumnName = "teacher_id"))
    private Set<Teacher> teachers = new HashSet<>();

    public Student() {
    }

    public Student(String studentName, Date studentDob, Grade grade, BankAccount bankAccount, Set<Teacher> teachers) {
        this.studentName = studentName;
        this.studentDob = studentDob;
        this.bankAccount = bankAccount;
        this.grade = grade;
        this.teachers = teachers;
    }

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

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public BankAccount getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Set<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(Set<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("\nStudent Id: ").append(studentId)
                .append("\nStudent Name: ").append(studentName)
                .append("\nStudent DOB: ").append(studentDob)
                .append("\nStudent Age: ").append(DateUtil.calculateYearDifference(studentDob))
                .toString();
    }
}
