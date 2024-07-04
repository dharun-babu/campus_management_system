package com.i2i.app.service;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dto.TeacherResponseDto;
import com.i2i.app.model.Teacher;

import java.util.List;

public interface TeacherInterface {
    List<TeacherResponseDto> getAllTeachers() throws StudentException;
    Teacher findTeacherBySubject(String subject) throws StudentException;

    TeacherResponseDto getTeacherById(int id) throws StudentException;

    void deleteTeacher(int id) throws StudentException;

    TeacherResponseDto getTeacherBySubject(String subject);
}
