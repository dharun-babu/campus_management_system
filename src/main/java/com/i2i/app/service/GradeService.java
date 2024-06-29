package com.i2i.app.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dao.GradeDAO;
import com.i2i.app.model.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p> This class manages operations related to the Grade entity.
 * It provides methods for creating or retrieving grades and managing interactions with storage.
 * This service interacts with the storage layer class for underlying operations.</p>
 */
@Service
public class GradeService {

    private static final Logger logger = LogManager.getLogger(GradeService.class);
    @Autowired
    private GradeDAO gradeDAO;

    /**
     * <p> Determines the section ('A', 'B', 'C', or 'D') for a given standard.
     * This method checks the number of students in each section of the specified standard
     * and assigns a section with fewer than 2 students. If all sections are filled, it assigns 'D'.</p>
     *
     * @param standard the standard/class level for which the section needs to be determined.
     * @return the section character for the given standard.
     * @throws StudentException if an error occurs while retrieving the grade information.
     */
    private char getSection(int standard) throws StudentException {
        logger.info("Determining section for standard: {}", standard);
        Grade grade = gradeDAO.getGradeByStandardAndSection(standard, 'A');
        if (grade != null) {
            char section = (grade.getStudents().size() < 2) ? 'A'
                    : (gradeDAO.getGradeByStandardAndSection(standard, 'B').getStudents().size() < 2) ? 'B'
                    : (gradeDAO.getGradeByStandardAndSection(standard, 'C').getStudents().size() < 2) ? 'C' : 'D';
            logger.info("Section determined: {}", section);
            return section;
        }
        logger.info("No existing grades found for standard: {}. Assigning section 'A'.", standard);
        return 'A';
    }

    /**
     * <p> Retrieves an existing grade or creates a new grade for a given standard.
     * This method determines the appropriate section for the standard and either returns an existing
     * grade or creates a new one if none exists for the specified standard and section.</p>
     *
     * @param standard the standard/class level for which the grade needs to be retrieved or created.
     * @return the Grade for the given standard and section.
     * @throws StudentException if an error occurs while retrieving or creating the grade.
     */
    public Grade getOrCreateGrade(int standard) throws StudentException {
        logger.info("Retrieving or creating grade for standard: {}", standard);
        char section = getSection(standard);
        Grade grade = gradeDAO.getGradeByStandardAndSection(standard, section);
        if (grade == null) {
            logger.warn("Grade not found for standard: {}, section: {}. Creating new grade.", standard, section);
        }
        return grade;
    }

    /**
     * <p> Retrieves a list of all grades.
     * This method fetches all Grade records stored.</p>
     *
     * @return a list of all grades.
     * @throws StudentException if an error occurs while retrieving the grades.
     */
    public List<Grade> getAllGrades() throws StudentException {
        logger.info("Retrieving all grades");
        return gradeDAO.getAllGrades();
    }

    /**
     * <p> Retrieves a specific grade based on the standard and section.
     * This method fetches a Grade for the specified standard and section.</p>
     *
     * @param standard the standard/class level for which the grade needs to be retrieved.
     * @param section the section (A, B, C, etc.) for which the grade needs to be retrieved.
     * @return the Grade for the given standard and section.
     * @throws StudentException if an error occurs while retrieving the grade.
     */
    public Grade getGrade(int standard, char section) throws StudentException {
        logger.info("Retrieving grade for standard: {}, section: {}", standard, section);
        return gradeDAO.getGradeByStandardAndSection(standard, section);
    }
}
