package com.i2i.app.service;

import java.util.List;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dao.GradeDAO;
import com.i2i.app.model.Grade;

/**
 * This class manages operations related to the Grade entity.
 * It provides methods for creating or retrieving grades and managing interactions with storage.
 * This service interacts with the storage layer class for underlying operations.
 */
public class GradeService {

    private GradeDAO gradeDAO = new GradeDAO();

    /**
     * <p> Determines the section ('A', 'B', 'C', or 'D') for a given standard.
     * This method checks the number of students in each section of the specified standard
     * and assigns a section with fewer than 2 students. If all sections are filled, it assigns 'D'.</p>
     *
     * @param standard      the standard/class level for which the section needs to be determined.
     * @return the section character for the given standard.
     */
    private char getSection(int standard) throws StudentException {
        Grade grade = gradeDAO.getGradeByStandardAndSection(standard, 'A');
        if (grade != null) {
            return (grade.getStudents().size() < 2) ? 'A' 
                : (gradeDAO.getGradeByStandardAndSection(standard, 'B').getStudents().size() < 2) ? 'B'
                : (gradeDAO.getGradeByStandardAndSection(standard, 'C').getStudents().size() < 2) ? 'C' : 'D';
        } 
        return 'A';
    }

    /**
     * <p> Retrieves an existing grade or creates a new grade for a given standard.
     * This method determines the appropriate section for the standard and either returns an existing
     * grade or creates a new one if none exists for the specified standard and section.</p>
     *
     * @param standard      the standard/class level for which the grade needs to be retrieved or created.
     * @return the Grade for the given standard and section.
     */
    public Grade getOrCreateGrade(int standard) throws StudentException {
        char section = getSection(standard);
        Grade grade = gradeDAO.getGradeByStandardAndSection(standard, section);
        if (grade != null) {
            return grade;
        } else {
            grade = new Grade(standard, section);
        }
        return grade;
    }

    /**
     * <p> Retrieves a list of all grades.
     * This method to fetch all Grade records stored.</p>
     *
     * @return a list of all grades.
     */
    public List<Grade> getAllGrades() throws StudentException {
        return gradeDAO.getAllGrades();
    }
    
    /**
     * <p> Retrieves a specific grade based on the standard and section.
     * This method to fetch a Grade for the specified standard and section.</p>
     *
     * @param standard      the standard/class level for which the grade needs to be retrieved.
     * @param section       the section (A, B, C, etc.) for which the grade needs to be retrieved.
     * @return the Grade for the given standard and section.
     */
    public Grade getGrade(int standard, char section) throws StudentException {
        return gradeDAO.getGradeByStandardAndSection(standard, section);
    }
}
