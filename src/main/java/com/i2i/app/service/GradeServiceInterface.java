package com.i2i.app.service;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dto.GradeResponseDto;
import com.i2i.app.model.Grade;

import java.util.List;

public interface GradeServiceInterface {
    List<GradeResponseDto> getAllGrades() throws StudentException;
    Grade getGradeByStandard(int standard) throws StudentException;
    void modifyCountByStandardAndSection(int standard, char section) throws StudentException;
    GradeResponseDto getGradeByStandardAndSection(int standard, char section) throws StudentException;
}
