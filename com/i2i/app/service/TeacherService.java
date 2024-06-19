package com.i2i.app.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dao.TeacherDAO;
import com.i2i.app.model.Teacher;

/**
 * This class to manage Teacher-related operations.
 * Provides methods to fetch teachers by subject, standard, and group.
 */
public class TeacherService {
    private TeacherDAO teacherDAO = new TeacherDAO();

    /**
     * <p>Retrieves a set of teachers for the core subjects: Tamil, English, Maths, and Science.</p>
     *
     * @return A set of teachers for the specified subjects.
     */
    private Set<Teacher> getTeachersBySubject() throws StudentException {
        Set<Teacher> teachers = new HashSet<>();
        teachers.add(teacherDAO.getTeacherBySubject("tamil"));
        teachers.add(teacherDAO.getTeacherBySubject("english"));
        teachers.add(teacherDAO.getTeacherBySubject("maths"));
        teachers.add(teacherDAO.getTeacherBySubject("science"));
        return teachers;
    }

    /**
     * <p> Retrieves a set of teachers based on the given subject and group options.
     * The method selects teachers for standard core subjects, additional subjects,
     * and specific group subjects based on the options provided.</p>
     *
     * @param subjectOption Option for selecting standard or additional subjects.
     * @param groupOption   Option for selecting group-specific subjects.
     * @return A set of teachers based on the specified options.
     */
    public Set<Teacher> getTeachersForStandard(int subjectOption, int groupOption) throws StudentException {
        Set<Teacher> teachers = new HashSet<>();
        switch (subjectOption) {
            case 1: {
                return getTeachersBySubject();
            }
            case 2: {
                teachers = getTeachersBySubject();
                teachers.add(teacherDAO.getTeacherBySubject("social science"));
                return teachers;
            }
            case 3: {
                switch (groupOption) {
                    case 1: {
                        teachers = getTeachersBySubject();
                        teachers.add(teacherDAO.getTeacherBySubject("computer science"));
                        return teachers;
                    }
                    case 2: {
                        teachers = getTeachersBySubject();
                        teachers.add(teacherDAO.getTeacherBySubject("biology"));
                        return teachers;
                    }
                    case 3: {
                        teachers = getTeachersBySubject();
                        teachers.add(teacherDAO.getTeacherBySubject("commerce"));
                        return teachers;
                    }
                }
            }
        }
        return teachers;
    }

    /**
     * <p> Retrieves a list of all teachers.</p>
     *
     * @return A list of all teachers.
     */
    public List<Teacher> getAllTeachers() throws StudentException {
        return teacherDAO.getAllTeachers();
    }

    /**
     * <p> Retrieves a teacher based on the specified subject name.</p>
     *
     * @param subjectName The name of the subject to get the teacher for.
     * @return The teacher who teaches the specified subject.
     */
    public Teacher getTeacher(String subjectName) throws StudentException {
        return teacherDAO.getTeacherBySubject(subjectName);
    }

    /**
     * <p>Closes the DAO connection to release resources.</p>
     */
    public void closeConnection() throws StudentException {
        teacherDAO.shutDown();
    }
}
