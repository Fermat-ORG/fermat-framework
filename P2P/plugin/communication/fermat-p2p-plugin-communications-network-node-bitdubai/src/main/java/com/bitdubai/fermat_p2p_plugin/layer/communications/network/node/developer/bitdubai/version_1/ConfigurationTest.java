/*
 * @#ConfigurationTest.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1;

import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.database.entities.CheckedInClient;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.ConfigurationTest</code>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 16/11/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ConfigurationTest {
    private static SessionFactory sessionFactory = null;

    private static SessionFactory configureSessionFactory() throws HibernateException {
        Configuration configuration = new Configuration();
        configuration.configure();

        Properties properties = configuration.getProperties();
        sessionFactory = configuration.buildSessionFactory();

        return sessionFactory;
    }

    public static void main(String[] args) {
        // Configure the session factory
        configureSessionFactory();

        Session session = null;
        Transaction tx=null;

        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            // Creating Contact entity that will be save to the sqlite database
            CheckedInClient checkedInClient = new  CheckedInClient();
            checkedInClient.setIdentityPublicKey("CLAVE");
            checkedInClient.setCheckedInTimestamp(new Timestamp(System.currentTimeMillis()));
            checkedInClient.setDeviceType("TEST");
            checkedInClient.setLongitude(new Double("1.00"));
            checkedInClient.setLatitude(new Double("2.00"));

            // Saving to the database
            session.save(checkedInClient);

            // Committing the change in the database.
            session.flush();
            tx.commit();

            // Fetching saved data
            List<CheckedInClient> contactList = session.createQuery("from CheckedInClient").list();

            for (CheckedInClient it : contactList) {
                System.out.println("Id: " + it.getIdentityPublicKey());
            }

        } catch (Exception ex) {
            ex.printStackTrace();

            // Rolling back the changes to make the data consistent in case of any failure
            // in between multiple database write operations.
            tx.rollback();
        } finally{
            if(session != null) {
                session.close();
            }
        }
    }
}
