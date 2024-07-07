package com.i2i.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
public class UpdateRequestStudentDto {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String studentName;

    @NotNull
    @Past
    private Date studentDob;

    private CreateGradeRequestDto grade;
    private CreateBankAccountRequestDto bankAccount;
    private Set<String> subjects;

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

    public CreateGradeRequestDto getGrade() {
        return grade;
    }

    public void setGrade(CreateGradeRequestDto grade) {
        this.grade = grade;
    }

    public CreateBankAccountRequestDto getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(CreateBankAccountRequestDto bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Set<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(Set<String> subjects) {
        this.subjects = subjects;
    }
}
