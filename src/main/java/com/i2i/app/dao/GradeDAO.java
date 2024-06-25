package com.i2i.app.dao;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.helper.SessionFactoryProvider;
import com.i2i.app.model.Grade;

/**
 * This class manages operations related to Grade entities.
 * This class is responsible for retrieving and managing grade records, as well as managing connections and transactions.
 */
public class GradeDAO {

    private static final Logger logger = LogManager.getLogger(GradeDAO.class);
    private SessionFactoryProvider sessionFactoryProvider = SessionFactoryProvider.getInstance();
    private SessionFactory sessionFactory = sessionFactoryProvider.getSessionFactory();

    /**
     * <p> Retrieves a list of all Grade records.
     * This method fetches all grade entries available.</p>
     *
     * @return A list of all grades.
     * @throws StudentException If an error occurs during the retrieval process.
     */
    public List<Grade> getAllGrades() throws StudentException {
        logger.info("Retrieving all grades");
        try (Session session = sessionFactory.openSession()) {
            Query<Grade> query = session.createQuery("from Grade", Grade.class);
            List<Grade> grades = query.list();
            logger.info("All grades retrieved successfully");
            return grades;
        } catch (Exception e) {
            logger.error("An error occurred while retrieving all grade records.", e);
            throw new StudentException("An error occurred while retrieving all grade records.", e);
        }
    }

    /**
     * <p> Retrieves a Grade record by its standard and section.
     * This method fetches a grade entry based on the provided standard and section.</p>
     *
     * @param standard    The standard of the grade to be retrieved.
     * @param section     The section of the grade to be retrieved.
     * @return The Grade with the specified standard and section, or null if no such grade exists.
     * @throws StudentException If an error occurs during the retrieval process by given standard and section.
     */
    public Grade getGradeByStandardAndSection(int standard, char section) throws StudentException {
        logger.info("Retrieving grade for standard: {} and section: {}", standard, section);
        try (Session session = sessionFactory.openSession()) {
            Query<Grade> query = session.createQuery("from Grade where standard = :standard and section = :section", Grade.class);
            query.setParameter("standard", standard);
            query.setParameter("section", section);
            Grade grade = query.uniqueResult();
            if (grade != null) {
                logger.info("Grade retrieved successfully for standard: {} and section: {}", standard, section);
            } else {
                logger.warn("No grade found for standard: {} and section: {}", standard, section);
            }
            return grade;
        } catch (Exception e) {
            logger.error("An error occurred while retrieving the grade record with standard {} and section {}", standard, section, e);
            throw new StudentException("An error occurred while retrieving the grade record with standard " + standard + " and section " + section, e);
        }
    }
}
