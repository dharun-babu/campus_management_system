package com.i2i.app.helper;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

import com.i2i.app.customexception.StudentException;

/**
 * This class that provides a Hibernate SessionFactory instance.
 * This class manages the creation and retrieval of the SessionFactory.
 */
public class SessionFactoryProvider {
    
    private static SessionFactoryProvider instance;
    private SessionFactory sessionFactory;

    /**
     * <p> Constructs a sessionFactoryProvider and initializes the sessionFactory.
     * This method attempts to build the sessionFactory using configuration files.
     * If an exception occurs during configuration, it prints the stack trace.</p>
     */
    private SessionFactoryProvider() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Error occurred while configuring SessionFactory.");
            e.printStackTrace();
        }
    }

    /**
     * <p> Retrieves the singleton instance of SessionFactoryProvider.</p>
     *
     * @return sessionFactoryProvider The singleton instance of SessionFactoryProvider.
     */
    public static SessionFactoryProvider getInstance() {
        if (null == instance) {
            instance = new SessionFactoryProvider();
        }
        return instance;
    }

    /**
     * <p> Retrieves the Hibernate SessionFactory instance.</p>
     *
     * @return sessionfactory The Hibernate SessionFactory instance.
     */
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * <p> Shuts down the provided SessionFactory.
     * This method closes the Hibernate SessionFactory to release resources.</p>
     *
     * @param sessionFactory The SessionFactory instance to shut down.
     */
    public static void shutDown(SessionFactory sessionFactory) {
        if (sessionFactory != null) {
            sessionFactory.close();
        }
    }
}
