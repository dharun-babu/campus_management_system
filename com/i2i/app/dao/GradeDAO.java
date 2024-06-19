package com.i2i.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.helper.SessionFactoryProvider;
import com.i2i.app.model.Grade;

/**
 * This class for managing Grade entities and to perform CRUD operations on Grade data.
 */
public class GradeDAO {
	private SessionFactoryProvider sessionFactoryProvider = SessionFactoryProvider.getInstance();
	private SessionFactory sessionFactory = sessionFactoryProvider.getSessionFactory();
    
	/**
	 * <p> Retrieves a list of all Grade entities from the database.</p>
	 *
	 * @return a List of Grades
	 * @throws StudentException if an error occurs during retrieval
	 */
	public List<Grade> getAllGrades() throws StudentException {
		try (Session session = sessionFactory.openSession()) {
			 Query query = session.createQuery("from Grade");
			 List<Grade> grades = query.list();
			 return grades;
		} catch (Exception e) {
			 throw new StudentException("Error occurred while retrieving all grade details", e);
		}
	}
	
	/**
	 * <p> Retrieves a Grade entity by its standard and section from the database.</p>
	 *
	 * @param standard the standard of the grade
	 * @param section  the section of the grade
	 * @return the Grade with the specified standard and section
	 * @throws StudentException if an error occurs during retrieval
	 */
	public Grade getGradeByStandardAndSection(int standard, char section) throws StudentException {
		try (Session session = sessionFactory.openSession()) {
			 Query query = session.createQuery("from Grade where standard = :standard and section = :section");
			 query.setParameter("standard", standard);
			 query.setParameter("section", section);
			 Grade grade = (Grade) query.uniqueResult();
			 return grade;
		} catch (Exception e) {
			 throw new StudentException("Error occurred while retrieving grade details by standard " + standard + " and section " + section, e);
		}
	}

	/**
	 * <p> Closes the session factory to release resources.</p>
	 */
	public void shutDown() {
		 if (null != sessionFactory) {
			 SessionFactoryProvider.shutDown(sessionFactory);
		 }
	}
}
