package com.i2i.app.dto;

import java.util.UUID;

public class GradeTransactionalDto {
    private UUID gradeId;
    private int standard;
    private char section;
    private int countOfStudent;

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
}
