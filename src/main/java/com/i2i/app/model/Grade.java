package com.i2i.app.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "grade")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "grade_id", updatable = false, nullable = false, columnDefinition = "BINARY(16)")
    private UUID gradeId;

    @Column(name = "standard", precision = 2, nullable = false)
    private int standard;

    @Column(name = "section", length = 1, nullable = false)
    private char section;

    @Column(name = "count_of_persons", length = 2, nullable = false)
    private int countOfStudent;

    @JsonIgnore
    @OneToMany(mappedBy = "grade", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Set<Student> students = new HashSet<>();

    public Grade() {
    }

    public Grade(UUID gradeId, int standard, char section, int countOfStudent, Set<Student> students) {
        this.standard = standard;
        this.section = section;
        this.countOfStudent = countOfStudent;
        this.students = students;
    }

    public Grade(int standard, char section, int countOfStudent) {
        this.standard = standard;
        this.section = section;
        this.countOfStudent = countOfStudent;
    }

    public UUID getGradeId() {
        return gradeId;
    }

    public void setGradeId(UUID gradeId) {
        this.gradeId = gradeId;
    }

    public int getStandard() {
        return standard;
    }

    public void setStandard(int standard) {
        this.standard = standard;
    }

    public char getSection() {
        return section;
    }

    public void setSection(char section) {
        this.section = section;
    }

    public int getCountOfStudent() {
        return countOfStudent;
    }

    public void setCountOfStudent(int countOfStudent) {
        this.countOfStudent = countOfStudent;
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
                .append("\nStandard: ").append(gradeId)
                .append("\nSection: ").append(section)
                .toString();
    }
}
