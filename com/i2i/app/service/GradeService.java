package com.i2i.app.service;

import java.util.ArrayList;
import java.util.List;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dao.GradeDAO;
import com.i2i.app.model.Grade;

/**
 * This class for managing Grade-related operations.
 * Provides methods to create or retrieve and manage the underlying database connections.
 */
public class GradeService {
    
    private GradeDAO gradeDAO = new GradeDAO();

    /**
     * <p> Determines the section ('A', 'B', 'C', or 'D') for a given standard.
     * Checks the number of students in each section and assigns a section
     * with less than 2 students or 'D' if all sections are filled.</p>
     *
     * @param standard the standard/class level to get the section for.
     * @return the section character for the given standard.
     */
    private char getSection(int standard) throws StudentException {
        Grade grade = gradeDAO.getGradeByStandardAndSection(standard, 'A');
        if (null != grade) {
            return (grade.getStudents().size() < 2) ? 'A' 
                : (gradeDAO.getGradeByStandardAndSection(standard, 'B').getStudents().size() < 2) ? 'B'
                : (gradeDAO.getGradeByStandardAndSection(standard, 'C').getStudents().size() < 2) ? 'C' : 'D';
        } 
        return 'A';
    }

    /**
     *<p>Retrieves an existing grade or creates a new grade for a given standard.
     * Determines the appropriate section and either returns an existing grade 
     * or creates a new one if none exists.</p>
     *
     * @param standard the standard/class level to get or create the grade for.
     * @return the Grade for the given standard and section.
     */
    public Grade getOrCreateGrade(int standard) throws StudentException {
        char section = getSection(standard);
        Grade grade = gradeDAO.getGradeByStandardAndSection(standard, section);
        if (null != grade) {
            return grade;
        } else {
            grade = new Grade(standard, section);
        }
        return grade;
    }

    /**
     * Retrieves a list of all grades from the database.
     *
     * @return a list of all Grade objects.
     * @throws StudentException if an error occurs during database access.
     */
    public List<Grade> getAllGrades() throws StudentException {
        return gradeDAO.getAllGrades();
    }
    
    /**
     * <p> Retrieves a specific grade based on the standard and section.</p>
     *
     * @param standard the standard/class level to get the grade for.
     * @param section the section (A, B, C, etc.) to get the grade for.
     * @return the Grade for the given standard and section.
     */
    public Grade getGrade(int standard, char section) throws StudentException {
        return gradeDAO.getGradeByStandardAndSection(standard, section);
    }

    /**
     * <p> Closes the database connection managed by the GradeDAO.</p>
     */
    public void closeConnection() throws StudentException {
        gradeDAO.shutDown();
    }
}
