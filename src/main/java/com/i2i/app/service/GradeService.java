package com.i2i.app.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dto.GradeResponseDto;
import com.i2i.app.mapper.MapperInterface;
import com.i2i.app.model.Grade;
import com.i2i.app.repositories.GradeRepository;

/**
 * This class handles the operations related to grade management.
 */
@Service
public class GradeService implements GradeServiceInterface {

    private static final Logger logger = LoggerFactory.getLogger(GradeService.class);

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private MapperInterface mapperInterface;

    /**
     * <p>
     * Retrieves all grades and maps them to response DTOs.
     * </p>
     *
     * @return List of GradeResponseDto
     * @throws StudentException if unable to retrieve grade details
     */
    @Override
    public List<GradeResponseDto> getAllGrades() throws StudentException {
        try {
            logger.info("Fetching all grade details");
            List<Grade> grades = gradeRepository.findAll();
            return mapperInterface.convertToGradeResponseDto(grades);
        } catch (Exception e) {
            logger.error("Error retrieving all grade details", e);
            throw new StudentException("Unable to retrieve all grade details", e);
        }
    }

    /**
     * <p>
     * Retrieves a grade by its standard and increments the student count if it is less than 2.
     * </p>
     *
     * @param standard The standard in range of (1-12) to search for grade.
     * @return Grade if found and updated, else null
     * @throws StudentException if unable to retrieve the grade by standard
     */
    @Override
    @Transactional
    public Grade getGradeByStandard(int standard) throws StudentException {
        try {
            logger.info("Fetching grade details for standard: {}", standard);
            List<Grade> grades = gradeRepository.getGradeByStandard(standard);
            for (Grade grade : grades) {
                if (grade.getCountOfStudent() < 2) {
                    grade.setCountOfStudent(grade.getCountOfStudent() + 1);
                    Grade updatedGrade = gradeRepository.save(grade);
                    logger.info("Updated grade details for standard: {}", standard);
                    return updatedGrade;
                }
            }
            return null;
        } catch (Exception e) {
            logger.error("Error retrieving grade by standard: {}", standard, e);
            throw new StudentException("Unable to retrieve grade by standard", e);
        }
    }

    /**
     * <p>
     * Decrements the student count for a grade identified by standard and section.
     * </p>
     *
     * @param standard The standard in range of (1-12) to modify the section count.
     * @param section The section in (A, B, C, D) for modify the the section count.
     * @throws StudentException if unable to retrieve the grade by standard and section
     */
    @Override
    public void modifyCountByStandardAndSection(int standard, char section) throws StudentException {
        try {
            logger.info("Modifying student count for standard: {} and section: {}", standard, section);
            Grade grade = gradeRepository.findGardeByStandardAndSection(standard, section);
            grade.setCountOfStudent((0 < grade.getCountOfStudent()) ? - 1 : 0);
            gradeRepository.save(grade);
            logger.info("Modified student count for standard: {} and section: {}", standard, section);
        } catch (Exception e) {
            logger.error("Error modifying student count for standard: {} and section: {}", standard, section, e);
            throw new StudentException("Unable to modify the grade details by standard and section", e);
        }
    }

    /**
     * <p>
     * Retrieves a grade by its standard and section and maps it to a response DTO.
     * </p>
     *
     * @param standard The standard to search for get grade.
     * @param section The section to search for get grade.
     * @return GradeResponseDto if found
     * @throws StudentException if unable to retrieve the grade by standard and section
     */
    @Override
    public GradeResponseDto getGradeByStandardAndSection(int standard, char section) throws StudentException {
        try {
            logger.debug("Fetching grade details for standard: {} and section: {}", standard, section);
            return mapperInterface.convertToGradeResponseDto(gradeRepository.getGradeByStandardAndSection(standard, section));
        } catch (Exception e) {
            logger.error("Error retrieving grade by standard: {} and section: {}", standard, section, e);
            throw new StudentException("Unable to retrieve the grade by standard and section", e);
        }
    }
}
