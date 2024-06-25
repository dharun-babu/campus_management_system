package com.i2i.app.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.dao.TeacherDAO;
import com.i2i.app.model.Teacher;

/**
 * <p> This class manages operations related to teachers.
 * It provides methods to retrieve teachers based on subject, standard, and group.
 * This service interacts with the storage layer class for underlying operations.</p>
 */
public class TeacherService {

    private static final Logger logger = LogManager.getLogger(TeacherService.class);
    private TeacherDAO teacherDAO = new TeacherDAO();

    /**
     * <p> Retrieves a set of teachers for the core subjects: Tamil, English, Maths, and Science.
     * This method fetches teachers who teach these core subjects.</p>
     *
     * @return A set of teachers for the core subjects.
     * @throws StudentException if an error occurs while retrieving the teachers.
     */
    private Set<Teacher> getTeachersByCoreSubject() throws StudentException {
        logger.info("Retrieving teachers for core subjects: Tamil, English, Maths, Science.");
        Set<Teacher> teachers = new HashSet<>();
        teachers.add(teacherDAO.getTeacherBySubject("tamil"));
        teachers.add(teacherDAO.getTeacherBySubject("english"));
        teachers.add(teacherDAO.getTeacherBySubject("maths"));
        teachers.add(teacherDAO.getTeacherBySubject("science"));
        logger.info("Core subject teachers retrieved successfully.");
        return teachers;
    }

    /**
     * <p> Retrieves a set of teachers based on the given subject and group options.
     * This method selects teachers for standard core subjects, additional subjects,
     * and specific group subjects based on the options provided.</p>
     *
     * @param subjectOption Option for selecting standard or additional subjects.
     * @param groupOption   Option for selecting group-specific subjects.
     * @return A set of teachers based on the specified options.
     * @throws StudentException if an error occurs while retrieving the teachers.
     */
    public Set<Teacher> getTeachersForStandard(int subjectOption, int groupOption) throws StudentException {
        logger.info("Retrieving teachers for subjectOption: {}, groupOption: {}", subjectOption, groupOption);
        Set<Teacher> teachers = new HashSet<>();
        switch (subjectOption) {
            case 1:
                return getTeachersByCoreSubject();
            case 2:
                teachers = getTeachersByCoreSubject();
                teachers.add(teacherDAO.getTeacherBySubject("social science"));
                logger.info("Teachers for core subjects and social science retrieved successfully.");
                return teachers;
            case 3:
                switch (groupOption) {
                    case 1:
                        teachers = getTeachersByCoreSubject();
                        teachers.add(teacherDAO.getTeacherBySubject("computer science"));
                        logger.info("Teachers for core subjects and computer science retrieved successfully.");
                        return teachers;
                    case 2:
                        teachers = getTeachersByCoreSubject();
                        teachers.add(teacherDAO.getTeacherBySubject("biology"));
                        logger.info("Teachers for core subjects and biology retrieved successfully.");
                        return teachers;
                    case 3:
                        teachers = getTeachersByCoreSubject();
                        teachers.add(teacherDAO.getTeacherBySubject("commerce"));
                        logger.info("Teachers for core subjects and commerce retrieved successfully.");
                        return teachers;
                }
        }
        logger.warn("Invalid subjectOption or groupOption provided: subjectOption: {}, groupOption: {}", subjectOption, groupOption);
        return teachers;
    }

    /**
     * <p> Retrieves a list of all teachers.
     * This method fetches all teachers stored.</p>
     *
     * @return A list of all teachers.
     * @throws StudentException if an error occurs while retrieving the teachers.
     */
    public List<Teacher> getAllTeachers() throws StudentException {
        logger.info("Retrieving all teachers.");
        List<Teacher> teachers = teacherDAO.getAllTeachers();
        logger.info("All teachers retrieved successfully.");
        return teachers;
    }

    /**
     * <p> Retrieves a teacher based on the specified subject name.
     * This method fetches a teacher who teaches the specified subject.</p>
     *
     * @param subjectName The name of the subject to get the teacher for.
     * @return The teacher who teaches the specified subject.
     * @throws StudentException if an error occurs while retrieving the teacher.
     */
    public Teacher getTeacher(String subjectName) throws StudentException {
        logger.info("Retrieving teacher for subject: {}", subjectName);
        Teacher teacher = teacherDAO.getTeacherBySubject(subjectName);
        if (teacher == null) {
            logger.warn("No teacher found for subject: {}", subjectName);
        } else {
            logger.info("Teacher retrieved successfully for subject: {}", subjectName);
        }
        return teacher;
    }
}
