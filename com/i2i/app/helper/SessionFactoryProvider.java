package com.i2i.app.helper;

import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

import com.i2i.app.customexception.StudentException;

public class SessionFactoryProvider {
    private static SessionFactoryProvider instance; 
    private  SessionFactory sessionFactory = getSessionFactory();

   /**
    * <p>The method is used to build the SessionFactory.
    * <p>
    * @return SessionFactory instance otherwise null
    */
    private SessionFactoryProvider () {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Exception e) {
            System.out.println("Error ocured while configuring SessionFactory.");
			e.printStackTrace();
        }
    }

    /**
     * <p>Method to retrieve the SessionFactory instance.
     * </p>
     * @return SessionFactory instance
     */
    public static SessionFactoryProvider getInstance() {
       if(null == instance) {
           instance = new SessionFactoryProvider();
       }
       return instance;
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    
   /**
    * <p>Method to shutdown the SessionFactory.
    * </p>
    */ 
    public static void shutDown(SessionFactory sessionFactory) {
        sessionFactory.close();
    }
}
