package com.i2i.app.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.helper.SessionFactoryProvider;
import com.i2i.app.model.Grade;

/**
 * This class manages operations related to Grade entities.
 * This class responsible for retrieving and managing grade records, as well as managing connections and transactions.
 */
public class GradeDAO {

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
        try (Session session = sessionFactory.openSession()) {
            Query<Grade> query = session.createQuery("from Grade", Grade.class);
            return query.list();
        } catch (Exception e) {
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
        try (Session session = sessionFactory.openSession()) {
            Query<Grade> query = session.createQuery("from Grade where standard = :standard and section = :section", Grade.class);
            query.setParameter("standard", standard);
            query.setParameter("section", section);
            return query.uniqueResult();
        } catch (Exception e) {
            throw new StudentException("An error occurred while retrieving the grade record with standard " + standard + " and section " + section, e);
        }
    }

    /**
     * <p> Closes the session factory to release resources.
     * This method ensures that all resources are properly released.</p>
     */
    public void shutDown() {
        if (sessionFactory != null) {
            sessionFactoryProvider.shutDown(sessionFactory);
        }
    }
}
