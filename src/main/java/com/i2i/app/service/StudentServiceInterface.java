package com.i2i.app.service;

import java.util.Date;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dto.CreateStudentRequestDto;
import com.i2i.app.dto.StudentResponseDto;
import com.i2i.app.model.Student;

import java.util.List;

public interface StudentServiceInterface {
    List<Student> getAllStudents() throws StudentException;
    Student getStudentById(String rollNumber) throws StudentException;
    Student saveStudent(String name, Date dob, int standard) throws StudentException;
    StudentResponseDto updateStudent(String rollNumber, CreateStudentRequestDto student) throws StudentException;
    void deleteStudent(String rollNumber) throws StudentException;
}
