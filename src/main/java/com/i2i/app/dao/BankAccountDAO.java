package com.i2i.app.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.i2i.app.customexception.StudentException;
import com.i2i.app.helper.SessionFactoryProvider;
import com.i2i.app.model.BankAccount;

/**
 * This class manages operations related to BankAccount entities.
 * This class responsible to verify the existence of a bank account number and manage the session factory.
 */
public class BankAccountDAO {

    private SessionFactoryProvider sessionFactoryProvider = SessionFactoryProvider.getInstance();
    private SessionFactory sessionFactory = sessionFactoryProvider.getSessionFactory();

    /**
     * <p> Checks if a bank account with the given account number exists.
     * This method queries the data source to determine if an account with the specified
     * account number is present.</p>
     *
     * @param accountNumber The account number to check.
     * @return true if the account number exists, false otherwise.
     * @throws StudentException If an error occurs during the check given account number.
     */
    public boolean existsByAccountNumber(long accountNumber) throws StudentException {
        try (Session session = sessionFactory.openSession()) {
            Query query = session.createQuery("select count(*) from BankAccount where account_number = :accountNumber");
            query.setParameter("accountNumber", accountNumber);
            Long count = (Long) query.uniqueResult();
            return (count != null && count > 0);
        } catch (Exception e) {
            throw new StudentException("An error occurred while checking if account number " + accountNumber + " exists.", e);
        }
    }
}
