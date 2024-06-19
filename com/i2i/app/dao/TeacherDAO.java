package com.i2i.app.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.helper.SessionFactoryProvider;
import com.i2i.app.model.Teacher;

/**
 * This class for managing Teacher entities and to perform CRUD operations on Teacher data.
 */
public class TeacherDAO {
	private SessionFactoryProvider sessionFactoryProvider = SessionFactoryProvider.getInstance();
	private SessionFactory sessionFactory = sessionFactoryProvider.getSessionFactory();
    
	/**
	 * <p> Retrieves a Teacher entity by the subject they teach from the database.</p>
	 *
	 * @param subject the subject taught by the teacher
	 * @return the Teacher that teaches the specified subject
	 * @throws StudentException if an error occurs during retrieval
	 */
	public Teacher getTeacherBySubject(String subject) throws StudentException {
		try (Session session = sessionFactory.openSession()) {
			 Query query = session.createQuery("from Teacher where subject = :subject");
			 query.setParameter("subject", subject);
			 Teacher teacher = (Teacher) query.uniqueResult();
			 return teacher;
		} catch (Exception e) {
			 throw new StudentException("Error occurred while retrieving the teacher details by" + subject, e);
		}
	}
	
	/**
	 * <p> Retrieves a list of all Teacher entities from the database.</p>
	 *
	 * @return a List of Teachers
	 * @throws StudentException if an error occurs during retrieval
	 */
	public List<Teacher> getAllTeachers() throws StudentException {
		try (Session session = sessionFactory.openSession()) {
			 Query query = session.createQuery("from Teacher");
			 List<Teacher> teachers = query.list();
			 return teachers;
		} catch (Exception e) {
			 throw new StudentException("Error occurred while retrieving all teacher details", e);
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
