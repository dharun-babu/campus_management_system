package com.i2i.app.service;

import java.util.Date;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.model.Student;

import java.util.List;

public interface StudentServiceInterface {
    List<Student> getAllStudents() throws StudentException;
    Student getStudentById(int id) throws StudentException;
    Student saveStudent(String name, Date dob, int standard) throws StudentException;
    void deleteStudent(int id) throws StudentException;
}
