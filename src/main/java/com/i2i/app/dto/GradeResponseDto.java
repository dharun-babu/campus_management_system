package com.i2i.app.dto;

import java.util.Set;
import java.util.UUID;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeResponseDto {
    private UUID gradeId;
    private int standard;
    private char section;
    private int countOfStudent;
    private Set<StudentMapDto> students;

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

    public Set<StudentMapDto> getStudents() {
        return students;
    }

    public void setStudents(Set<StudentMapDto> students) {
        this.students = students;
    }
}
