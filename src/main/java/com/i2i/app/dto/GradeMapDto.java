package com.i2i.app.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeMapDto {
    private int standard;
    private char section;

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
}
