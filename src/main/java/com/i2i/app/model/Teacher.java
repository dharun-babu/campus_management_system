package com.i2i.app.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.i2i.app.util.DateUtil;

@Entity
@Table(name = "teacher")
public class Teacher {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_id")
    private int teacherId;

    @Column(name = "teacher_name", length = 50, nullable = false)
    private String teacherName;

    @Column(name = "subject", length = 20, nullable = false)
    private String subject;

    @Column(name = "date_of_join", nullable = false)
    private Date dateOfJoin;

    @ManyToMany(mappedBy = "teachers",fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Student> students = new HashSet<>();

    public Teacher() {
    }

    public Teacher(int teacherId, String teacherName, String subject, Date dateOfJoin, Set<Student> students) {
        this.teacherId = teacherId;
        this.teacherName = teacherName;
        this.subject = subject;
        this.dateOfJoin = dateOfJoin;
        this.students = students;
    }

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

    public Set<Student> getStudents() {
        return students;
    }

    public void setStudents(Set<Student> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("\nTeacher Name: ").append(teacherName)
                .append("\nExperience : ").append(DateUtil.calculateYearDifference(dateOfJoin))
                .toString();
    }
}
