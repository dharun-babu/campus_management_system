package com.i2i.app.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.helper.SessionFactoryProvider;
import com.i2i.app.model.Teacher;

/**
 * This class manages Teacher entities and performs CRUD operations related to Teacher data.
 */
public class TeacherDAO {

    private static final Logger logger = LogManager.getLogger(TeacherDAO.class);
    private SessionFactoryProvider sessionFactoryProvider = SessionFactoryProvider.getInstance();
    private SessionFactory sessionFactory = sessionFactoryProvider.getSessionFactory();

    /**
     * <p> Retrieves a Teacher by the subject they teach.
     * This method fetches the data source to find a teacher who teaches the specified subject.</p>
     *
     * @param subject    The subject taught by the teacher.
     * @return The Teacher who teaches the specified subject.
     * @throws StudentException If an error occurs during retrieval of the teacher by the given subject.
     */
    public Teacher getTeacherBySubject(String subject) throws StudentException {
        logger.info("Retrieving teacher for subject: {}", subject);
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from Teacher where subject = :subject");
            query.setParameter("subject", subject);
            Teacher teacher = (Teacher) query.uniqueResult();
            if (teacher != null) {
                logger.info("Teacher retrieved successfully for subject: {}", subject);
            } else {
                logger.warn("No teacher found for subject: {}", subject);
            }
            return teacher;
        } catch (Exception e) {
            throw new StudentException("Error occurred while retrieving the teacher details for the subject: " + subject, e);
        }
    }

    /**
     * <p> Retrieves a list of all Teachers.
     * This method fetches the data source to get a list of all teachers.</p>
     *
     * @return A List of all Teachers.
     * @throws StudentException If an error occurs during retrieval, providing detailed context about the issue.
     */
    public List<Teacher> getAllTeachers() throws StudentException {
        logger.info("Retrieving all teachers");
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("from Teacher");
            List<Teacher> teachers = query.list();
            logger.info("All teachers retrieved successfully");
            return teachers;
        } catch (Exception e) {
            throw new StudentException("Error occurred while retrieving all teacher details.", e);
        }
    }
}
