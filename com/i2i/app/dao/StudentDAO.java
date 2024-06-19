package com.i2i.app.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.helper.SessionFactoryProvider;
import com.i2i.app.model.BankAccount;
import com.i2i.app.model.Grade;
import com.i2i.app.model.Student;
import com.i2i.app.util.DateUtil;

/**
 * This class for managing Student entities and to perform CRUD operations on Student data.
 */
public class StudentDAO {
	private SessionFactoryProvider sessionFactoryProvider = SessionFactoryProvider.getInstance();
    private SessionFactory sessionFactory = sessionFactoryProvider.getSessionFactory();

	/**
	 * <p>Inserts a new Student entity into the database.</p>
	 *
	 * @param student the Student  to be inserted
	 * @throws StudentException if an error occurs during the insertion
	 */
	public void insertStudent(Student student) throws StudentException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			 transaction = session.beginTransaction();
			 session.save(student);
			 session.getTransaction().commit();
		} catch (Exception e) {
			 if (null != transaction) {
				 transaction.rollback();
			 }
			 throw new StudentException("Error occurred while inserting a student", e);
		}
	}

	/**
	 * <p> Retrieves a list of all Student entities from the database.</p>
	 *
	 * @return a List of Students
	 * @throws StudentException if an error occurs during retrieval
	 */
	public List<Student> getAllStudents() throws StudentException {
		 try (Session session = sessionFactory.openSession()){
			  Query query = session.createQuery("from Student", Student.class);
			  List<Student> students = query.list();
			  return students;
		 } catch (Exception e) {
			  throw new StudentException("Error occurred while retrieving all students' details", e);
		 }
	}

	/**
	 * <p> Retrieves a Student entity by its ID from the database.</p>
	 *
	 * @param studentId the ID of the Student to be retrieved
	 * @return the Student with the specified ID
	 * @throws StudentException if an error occurs during retrieval
	 */
	public Student getStudentById(int studentId) throws StudentException {
		try (Session session = sessionFactory.openSession()){
			 return session.get(Student.class, studentId);
		} catch (Exception e) {
			 throw new StudentException("Error occurred while retrieving student with ID " + studentId, e);
		}
	}

	/**
	 * <p> Deletes a Student entity by its ID from the database.</p>
	 *
	 * @param studentId the ID of the Student to be deleted
	 * @return true if the Student was successfully deleted, false otherwise
	 * @throws StudentException if an error occurs during deletion
	 */
	public boolean deleteStudentById(int studentId) throws StudentException {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()){
			 transaction = session.beginTransaction();
			 Student student = session.get(Student.class, studentId);
			 if (null != student) {
				 session.delete(student);
				 session.getTransaction().commit();
				 return true;
			 }
			 return false;
		} catch (Exception e) {
	         if (null != transaction) {
				 transaction.rollback();
			 }
			 throw new StudentException("Error occurred while deleting student with ID " + studentId, e);
		}
	}

	/**
	 * <p> Closes the session factory to release resources.</p>
	 */
	public void shutDown() {
		 if (null != sessionFactory) {
			 sessionFactoryProvider.shutDown(sessionFactory);
		 }
	}
}
