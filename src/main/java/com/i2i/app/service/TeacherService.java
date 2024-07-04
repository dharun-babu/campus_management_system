package com.i2i.app.service;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dto.TeacherResponseDto;
import com.i2i.app.mapper.MapperInterface;
import com.i2i.app.model.Teacher;
import com.i2i.app.repositories.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This service class handles the operations related to teacher management.
 */
@Service
public class TeacherService implements TeacherInterface {

    private static final Logger logger = LoggerFactory.getLogger(TeacherService.class);

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private MapperInterface mapperInterface;

    /**
     * <p>
     * Retrieves all teachers and maps them to response DTOs.
     * </p>
     *
     * @return List of TeacherResponseDto
     * @throws StudentException if unable to retrieve teacher details
     */
    @Override
    public List<TeacherResponseDto> getAllTeachers() throws StudentException {
        try {
            logger.info("Fetching all teacher details");
            List<Teacher> teachers = teacherRepository.findAll();
            return mapperInterface.convertToTeacherResponseDto(teachers);
        } catch (Exception e) {
            logger.error("Error retrieving all teacher details", e);
            throw new StudentException("Unable to retrieve all teacher details", e);
        }
    }

    /**
     * <p>
     * Retrieves a teacher by the subject they teach.
     * </p>
     *
     * @param subject The subject to search for get teacher details
     * @return Teacher if found
     * @throws StudentException if unable to retrieve the teacher by subject
     */
    @Override
    public Teacher findTeacherBySubject(String subject) throws StudentException {
        try {
            logger.info("Fetching teacher details for subject: {}", subject);
            return teacherRepository.findTeacherBySubject(subject);
        } catch (Exception e) {
            logger.error("Error retrieving teacher by subject: {}", subject, e);
            throw new StudentException("Unable to retrieve the teacher by subject: " + subject, e);
        }
    }

    /**
     * <p>
     * Retrieves a teacher by their unique identifier and maps to a response DTO.
     * </p>
     *
     * @param id The unique identifier of the teacher
     * @return TeacherResponseDto if found
     * @throws StudentException if unable to retrieve the teacher by id
     */
    @Override
    public TeacherResponseDto getTeacherById(int id) throws StudentException {
        try {
            logger.info("Fetching teacher details for id: {}", id);
            Teacher teacher = teacherRepository.findById(id)
                    .orElseThrow(() -> new StudentException("Unable to retrieve teacher details by ID: " + id));
            return mapperInterface.convertToTeacherResponseDto(teacher);
        } catch (Exception e) {
            logger.error("Error retrieving teacher by id: {}", id, e);
            throw new StudentException("Unable to retrieve teacher details by ID: " + id, e);
        }
    }

    /**
     * <p>
     * Deletes a teacher by their unique identifier.
     * </p>
     *
     * @param id The unique identifier of the teacher
     * @throws StudentException if unable to delete the teacher
     */
    @Override
    public void deleteTeacher(int id) throws StudentException {
        try {
            logger.info("Deleting teacher details for id: {}", id);
            Teacher teacher = teacherRepository.findById(id).orElse(null);
            teacherRepository.delete(teacher);
            logger.info("Deleted teacher details for id: {}", id);
        } catch (Exception e) {
            logger.error("Error deleting teacher by id: {}", id, e);
            throw new StudentException("Unable to delete the teacher by id: " + id, e);
        }
    }

    /**
     * <p>
     * Retrieves a teacher by the subject they teach and maps to a response DTO.
     * </p>
     *
     * @param subject The subject to search for
     * @return TeacherResponseDto if found
     * @throws StudentException if unable to retrieve the teacher by subject
     */
    @Override
    public TeacherResponseDto getTeacherBySubject(String subject) throws StudentException {
        try {
            logger.info("Fetching teacher details for subject: {}", subject);
            Teacher teacher = teacherRepository.findTeacherBySubject(subject);
            return mapperInterface.convertToTeacherResponseDto(teacher);
        } catch (Exception e) {
            logger.error("Error retrieving teacher by subject: {}", subject, e);
            throw new StudentException("Unable to retrieve the teacher by subject: " + subject, e);
        }
    }
}
