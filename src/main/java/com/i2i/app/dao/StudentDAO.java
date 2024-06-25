package com.i2i.app.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.helper.SessionFactoryProvider;
import com.i2i.app.model.Student;

/**
 * <p> This class manages operations related to Student entities.
 * This class is responsible for creating, retrieving, and deleting student records, as well as managing connections and transactions.</p>
 */
public class StudentDAO {

    private static final Logger logger = LogManager.getLogger(StudentDAO.class);
    private SessionFactoryProvider sessionFactoryProvider = SessionFactoryProvider.getInstance();
    private SessionFactory sessionFactory = sessionFactoryProvider.getSessionFactory();

    /**
     * <p> Inserts a new Student record.
     * This method adds a new student entry with the provided details.</p>
     *
     * @param student The Student to be inserted.
     * @throws StudentException If an error occurs during the insertion process.
     */
    public void insertStudent(Student student) throws StudentException {
        logger.info("Inserting new student: {}", student);
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(student);
            transaction.commit();
            logger.info("Student inserted successfully: {}", student);
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                logger.warn("Transaction rolled back due to an error during student insertion: {}", student);
            }
            logger.error("Error occurred while inserting student: {}", student, e);
            throw new StudentException("An error occurred while inserting the student record.", e);
        }
    }

    /**
     * <p> Retrieves a list of all Student records.
     * This method fetches all student entries available.</p>
     *
     * @return A list of all students.
     * @throws StudentException If an error occurs during the retrieval process.
     */
    public List<Student> getAllStudents() throws StudentException {
        logger.info("Retrieving all students");
        try (Session session = sessionFactory.openSession()) {
            Query<Student> query = session.createQuery("from Student", Student.class);
            List<Student> students = query.list();
            logger.info("All students retrieved successfully");
            return students;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving all students", e);
            throw new StudentException("An error occurred while retrieving all student records.", e);
        }
    }

    /**
     * <p> Retrieves a Student record by its ID.
     * This method fetches a student entry based on the provided student ID.</p>
     *
     * @param studentId The ID of the student to be retrieved.
     * @return The Student with the specified ID, or null if no such student exists.
     * @throws StudentException If an error occurs during the retrieval process.
     */
    public Student getStudentById(int studentId) throws StudentException {
        logger.info("Retrieving student by ID: {}", studentId);
        try (Session session = sessionFactory.openSession()) {
            Student student = session.get(Student.class, studentId);
            if (student != null) {
                logger.info("Student retrieved successfully: {}", student);
            } else {
                logger.warn("No student found with ID: {}", studentId);
            }
            return student;
        } catch (Exception e) {
            logger.error("Error occurred while retrieving student with ID: {}", studentId, e);
            throw new StudentException("An error occurred while retrieving the student record with ID " + studentId, e);
        }
    }

    /**
     * <p> Deletes a Student record by its ID.
     * This method removes the student entry with the specified ID.</p>
     *
     * @param studentId The ID of the student to be deleted.
     * @return True if the student was successfully deleted, false otherwise.
     * @throws StudentException If an error occurs during the deletion process.
     */
    public boolean deleteStudentById(int studentId) throws StudentException {
        logger.info("Deleting student by ID: {}", studentId);
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            Student student = session.get(Student.class, studentId);
            if (student != null) {
                session.delete(student);
                transaction.commit();
                logger.info("Student deleted successfully: {}", student);
                return true;
            } else {
                logger.warn("No student found to delete with ID: {}", studentId);
                return false;
            }
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
                logger.warn("Transaction rolled back due to an error during student deletion with ID: {}", studentId);
            }
            logger.error("Error occurred while deleting student with ID: {}", studentId, e);
            throw new StudentException("An error occurred while deleting the student record with ID " + studentId, e);
        }
    }
}
