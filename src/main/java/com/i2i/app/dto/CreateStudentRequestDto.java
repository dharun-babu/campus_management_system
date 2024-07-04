package com.i2i.app.dto;

import lombok.*;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateStudentRequestDto {
    private String studentName;
    private Date studentDob;
    private CreateGradeRequestDto createGradeRequestDto;
    private CreateBankAccountRequestDto createBankAccountRequestDto;
    private Set<String> subjects;
}
