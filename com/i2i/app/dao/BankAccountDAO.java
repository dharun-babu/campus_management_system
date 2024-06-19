package com.i2i.app.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.helper.SessionFactoryProvider;
import com.i2i.app.model.BankAccount;

/**
 * This class for managing BankAccount entities and to perform operations related to BankAccount data.
 */
public class BankAccountDAO {
	private SessionFactoryProvider sessionFactoryProvider = SessionFactoryProvider.getInstance();
	private SessionFactory sessionFactory = sessionFactoryProvider.getSessionFactory();
    
	/**
	 * <p> Checks if a BankAccount entity with the given account number exists in the database.</p>
	 *
	 * @param accountNumber the account number to check
	 * @return true if the account number exists, false otherwise
	 * @throws StudentException if an error occurs during the check given account number
	 */
	public boolean existsByAccountNumber(long accountNumber) throws StudentException {
		try (Session session = sessionFactory.openSession()) {
			 Query query = session.createQuery("select count(*) from BankAccount where account_number = :accountNumber");
			 query.setParameter("accountNumber", accountNumber);
			 Long count = (Long) query.uniqueResult();
			 return (null == count || 0 == count) ? false : true;
		} catch (Exception e) {
			 throw new StudentException("Error occurred while checking if account number " + accountNumber + " exists", e);
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
