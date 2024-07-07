package com.i2i.app.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dto.GradeResponseDto;
import com.i2i.app.mapper.MapperInterface;
import com.i2i.app.model.Grade;
import com.i2i.app.repositories.GradeRepository;

/**
 * This class handling operations related to grade management.
 */
@Service
@Slf4j
public class GradeService implements GradeServiceInterface {

    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private MapperInterface mapperInterface;

    /**
     * <p>
     *     Generates a suitable section for the given standard.
     * </p>
     *
     * @param standard The standard for which to generate the section.
     * @return The generated section.
     * @throws StudentException if no suitable section is found.
     */
    public char createSectionForStandard(int standard) throws StudentException {
        char[] sections = {'A', 'B', 'C', 'D'};
        for (char section : sections) {
            Grade grade = gradeRepository.findGardeByStandardAndSection(standard, section);
            if (grade == null || grade.getCountOfStudent() < 2) {
                return section;
            }
        }
        throw new StudentException("No available section for standard: " + standard);
    }

    /**
     * <p>
     *     Retrieves all grades and maps them to response DTOs.
     * </p>
     *
     * @return List of {@link GradeResponseDto}.
     * @throws StudentException if unable to retrieve grade details.
     */
    @Override
    public List<GradeResponseDto> getAllGrades() throws StudentException {
        try {
            log.debug("Fetching all grade details");
            List<Grade> grades = gradeRepository.findAll();
            return mapperInterface.convertToGradeResponseDto(grades);
        } catch (Exception e) {
            throw new StudentException("Unable to retrieve all grade details", e);
        }
    }

    /**
     * <p>
     *     Retrieves a grade by its standard and increments the student count if it is less than 2.
     * </p>
     *
     * @param standard The standard to search for the grade.
     * @return Grade if found and updated, otherwise null.
     * @throws StudentException if unable to retrieve or create the grade by standard.
     */
    @Override
    @Transactional
    public Grade getGradeByStandard(int standard) throws StudentException {
        try {
            log.debug("Fetching grade details for standard: {}", standard);
            List<Grade> grades = gradeRepository.getGradeByStandard(standard);
            if (grades.isEmpty()) {
                char section = createSectionForStandard(standard);
                Grade grade = new Grade(standard, section, 1);
                return gradeRepository.save(grade);
            } else {
                for (Grade grade : grades) {
                    if (grade.getCountOfStudent() < 2) {
                        grade.setCountOfStudent(grade.getCountOfStudent() + 1);
                        Grade updatedGrade = gradeRepository.save(grade);
                        log.debug("Updated grade details for standard: {}", standard);
                        return updatedGrade;
                    }
                }
                char newSection = createSectionForStandard(standard);
                Grade newGrade = new Grade(standard, newSection, 1);
                log.debug("Creating new grade: {}", newGrade);
                return gradeRepository.save(newGrade);
            }
        } catch (Exception e) {
            throw new StudentException("Unable to retrieve or create grade by standard", e);
        }
    }

    /**
     * <p>
     *     Decrements the student count for a grade identified by standard and section.
     * </p>
     *
     * @param standard The standard of the grade to modify.
     * @param section  The section of the grade to modify.
     * @throws StudentException if unable to retrieve the grade by standard and section.
     */
    @Override
    public void modifyCountByStandardAndSection(int standard, char section) throws StudentException {
        try {
            log.debug("Modifying student count for standard: {} and section: {}", standard, section);
            Grade grade = gradeRepository.findGardeByStandardAndSection(standard, section);
            if (grade != null) {
                grade.setCountOfStudent((grade.getCountOfStudent() > 0) ? grade.getCountOfStudent() - 1 : 0);
                gradeRepository.save(grade);
                log.info("Modified student count for standard: {} and section: {}", standard, section);
            } else {
                log.warn("Grade not found for standard: {} and section: {}", standard, section);
                throw new StudentException("Grade not found for standard: " + standard + " and section: " + section);
            }
        } catch (Exception e) {
            throw new StudentException("Unable to modify the grade details by standard and section", e);
        }
    }

    /**
     * <p>
     *     Retrieves a grade by its standard and section and maps it to a response DTO.
     * </p>
     *
     * @param standard The standard to search for the grade.
     * @param section  The section to search for the grade.
     * @return {@link GradeResponseDto} if found.
     * @throws StudentException if unable to retrieve the grade by standard and section.
     */
    @Override
    public GradeResponseDto getGradeByStandardAndSection(int standard, char section) throws StudentException {
        try {
            log.debug("Fetching grade details for standard: {} and section: {}", standard, section);
            Grade grade = gradeRepository.getGradeByStandardAndSection(standard, section);
            if (grade != null) {
                return mapperInterface.convertToGradeResponseDto(grade);
            } else {
                log.warn("Grade not found for standard: {} and section: {}", standard, section);
                throw new StudentException("Grade not found for standard: " + standard + " and section: " + section);
            }
        } catch (Exception e) {
            throw new StudentException("Unable to retrieve the grade by standard and section", e);
        }
    }
}
