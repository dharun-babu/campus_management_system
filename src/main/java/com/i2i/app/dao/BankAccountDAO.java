package com.i2i.app.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.helper.SessionFactoryProvider;
import com.i2i.app.model.BankAccount;

/**
 * This class manages operations related to BankAccount entities.
 * This class is responsible for verifying the existence of a bank account number.
 */
public class BankAccountDAO {

    private static final Logger logger = LogManager.getLogger(BankAccountDAO.class);
    private SessionFactoryProvider sessionFactoryProvider = SessionFactoryProvider.getInstance();
    private SessionFactory sessionFactory = sessionFactoryProvider.getSessionFactory();

    /**
     * <p> Checks if a bank account with the given account number exists.
     * This method queries the data source to determine if an account with the specified
     * account number is present.</p>
     *
     * @param accountNumber The account number to check.
     * @return true if the account number exists, false otherwise.
     * @throws StudentException If an error occurs during the check for the given account number.
     */
    public boolean existsByAccountNumber(long accountNumber) throws StudentException {
        logger.info("Checking if account number {} exists", accountNumber);
        try (Session session = sessionFactory.openSession()) {
            Query<Long> query = session.createQuery("select count(*) from BankAccount where accountNumber = :accountNumber", Long.class);
            query.setParameter("accountNumber", accountNumber);
            Long count = query.uniqueResult();
            boolean exists = (count != null && count > 0);
            if (exists) {
                logger.info("Account number {} exists", accountNumber);
            } else {
                logger.warn("Account number {} does not exist", accountNumber);
            }
            return exists;
        } catch (Exception e) {
            throw new StudentException("An error occurred while checking if account number " + accountNumber + " exists.", e);
        }
    }
}
