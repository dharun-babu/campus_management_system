package com.i2i.app.helper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import io.github.cdimascio.dotenv.Dotenv;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

import com.i2i.app.customexception.StudentException;

/**
 * This class provides a Hibernate SessionFactory instance.
 * This class manages the creation and retrieval of the SessionFactory.
 */
public class SessionFactoryProvider {

    private static final Logger logger = LogManager.getLogger(SessionFactoryProvider.class);
    private static SessionFactoryProvider instance;
    private SessionFactory sessionFactory;

    /**
     * <p>Constructs a SessionFactoryProvider and initializes the SessionFactory.
     * This method attempts to build the SessionFactory using configuration files.
     * If an exception occurs during configuration, it logs the error.</p>
     */
    private SessionFactoryProvider() {
        try {
            logger.debug("Initializing SessionFactoryProvider.");
            Dotenv dotenv = Dotenv.configure().load();
            Configuration configuration = new Configuration();
            String logFilePath = dotenv.get("LOG_FILE_PATH");
            System.setProperty("LOG_FILE",logFilePath);
            configuration.setProperty("hibernate.connection.driver_class", dotenv.get("DB_DRIVER"));
            if (null != dotenv.get("DB_URL")) {
                configuration.setProperty("hibernate.connection.url", dotenv.get("DB_URL"));
            } else {
                logger.fatal("Database URL is missing in the environment configuration. SessionFactory initialization cannot proceed.");
            }
            configuration.setProperty("hibernate.connection.username", dotenv.get("DB_USERNAME"));
            configuration.setProperty("hibernate.connection.password", dotenv.get("DB_PASSWORD"));
            configuration.configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
            logger.info("SessionFactory initialized successfully.");
        } catch (Exception e) {
            logger.error("Error occurred while configuring SessionFactory.", e);
        }
    }

    /**
     * Retrieves the singleton instance of SessionFactoryProvider.
     *
     * @return sessionFactoryProvider The singleton instance of SessionFactoryProvider.
     */
    public static SessionFactoryProvider getInstance() {
        if (instance == null) {
            logger.info("Creating a new instance of SessionFactoryProvider.");
            instance = new SessionFactoryProvider();
        }
        return instance;
    }

    /**
     * Retrieves the Hibernate SessionFactory instance.
     *
     * @return sessionFactory The Hibernate SessionFactory instance.
     */
    public SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            logger.warn("SessionFactory is not initialized.");
        }
        return sessionFactory;
    }

    /**
     * Shuts down the provided SessionFactory.
     * This method closes the Hibernate SessionFactory to release resources.
     */
    public static void shutDown() {
        SessionFactory sessionFactory = getInstance().getSessionFactory();
        if (sessionFactory != null) {
            logger.info("Shutting down the SessionFactory.");
            sessionFactory.close();
        } else {
            logger.warn("SessionFactory is already null or not initialized.");
        }
    }
}
