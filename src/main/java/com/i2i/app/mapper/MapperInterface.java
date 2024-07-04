package com.i2i.app.mapper;

import com.i2i.app.dto.*;
import com.i2i.app.model.BankAccount;
import com.i2i.app.model.Grade;
import com.i2i.app.model.Student;
import com.i2i.app.model.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring")
public interface MapperInterface {

    MapperInterface MapperInterfaceInstance = Mappers.getMapper(MapperInterface.class);

    @Mapping(source = "studentId", target = "studentId")
    @Mapping(source = "studentName", target = "studentName")
    @Mapping(source = "studentDob", target = "studentDob")
    @Mapping(source = "student.grade", target = "gradeMapDto")
    @Mapping(source = "student.bankAccount", target = "bankAccountMapDto")
    @Mapping(source = "student.teachers", target = "teachers")
    StudentResponseDto convertToStudentResponseDto(Student student);

    List<StudentResponseDto> convertToStudentResponseDto(List<Student> students);

    @Mapping(source = "studentId", target = "studentId")
    @Mapping(source = "studentName", target = "studentName")
    @Mapping(source = "studentDob", target = "studentDob")
    StudentTransactionalDto convertToStudentTransactionDto(Student student);

    List<GradeResponseDto> convertToGradeResponseDto(List<Grade> grades);

    @Mapping(source = "gradeId", target = "gradeId")
    @Mapping(source = "standard", target = "standard")
    @Mapping(source = "section", target = "section")
    @Mapping(source = "countOfStudent", target = "countOfStudent")
    @Mapping(source = "students", target = "students")
    GradeResponseDto convertToGradeResponseDto(Grade grade);

    @Mapping(source = "standard", target = "standard")
    @Mapping(source = "section", target = "section")
    Grade convertToGrade(GradeTransactionalDto gradeTransactionalDto);

    @Mapping(source = "standard", target = "standard")
    @Mapping(source = "section", target = "section")
    GradeMapDto convertToGradeMapDto(Grade grade);

    @Mapping(source = "bankName", target = "bankName")
    @Mapping(source = "branchName", target = "branchName")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "ifscCode", target = "ifscCode")
    @Mapping(source = "mobileNumber", target = "mobileNumber")
    BankAccount convertToBankAccount(CreateBankAccountRequestDto createBankAccountRequestDto);

    @Mapping(source = "accountId", target = "accountId")
    @Mapping(source = "bankName", target = "bankName")
    @Mapping(source = "branchName", target = "branchName")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "ifscCode", target = "ifscCode")
    @Mapping(source = "mobileNumber", target = "mobileNumber")
    BankAccountResponseDto convertToBankAccountResponseDto(BankAccount bankAccount);

    @Mapping(source = "accountId", target = "accountId")
    @Mapping(source = "bankName", target = "bankName")
    @Mapping(source = "branchName", target = "branchName")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "ifscCode", target = "ifscCode")
    @Mapping(source = "mobileNumber", target = "mobileNumber")
    BankAccount convertToBankAccount(BankAccountResponseDto BankAccountResponseDto);

    @Mapping(source = "studentName", target = "studentName")
    @Mapping(source = "studentDob", target = "studentDob")
    StudentMapDto mapStudentToStudentMapDto(Student student);

    @Mapping(source = "bankName", target = "bankName")
    @Mapping(source = "branchName", target = "branchName")
    @Mapping(source = "accountNumber", target = "accountNumber")
    @Mapping(source = "ifscCode", target = "ifscCode")
    @Mapping(source = "mobileNumber", target = "mobileNumber")
    BankAccountMapDto convertToBankAccountMapDto (BankAccountResponseDto bankAccountResponseDto);

    @Mapping(source = "teacherId", target = "teacherId")
    @Mapping(source = "subject", target = "subject")
    @Mapping(source = "dateOfJoin", target = "dateOfJoin")
    Teacher convertToTeacher(TeacherResponseDto teacherResponseDto);

    @Mapping(source = "teacherId", target = "teacherId")
    @Mapping(source = "subject", target = "subject")
    @Mapping(source = "dateOfJoin", target = "dateOfJoin")
    TeacherResponseDto convertToTeacherResponseDto (Teacher teacher);

    List<TeacherResponseDto> convertToTeacherResponseDto (List<Teacher> teachers);
}
