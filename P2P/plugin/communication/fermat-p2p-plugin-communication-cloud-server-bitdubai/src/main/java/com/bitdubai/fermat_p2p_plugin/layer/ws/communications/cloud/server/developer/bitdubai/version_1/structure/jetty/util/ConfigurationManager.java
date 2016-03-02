/*
 * @#ConfigurationManager  - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util;

import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.JettyEmbeddedAppServer;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;
import org.bouncycastle.util.encoders.Base64;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.ConfigurationManager</code> implements
 * <p/>
 * <p/>
 * Created by Roberto Requena - (rart3001@gmail.com) on 24/02/16.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class ConfigurationManager {

    /**
     * Represent the logger instance
     */
    private static Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(ConfigurationManager.class));

    /**
     * Represent the value of DIR_NAME
     */
    public static final String DIR_NAME = "configuration";

    /**
     * Represent the value of FILE_NAME
     */
    public static final String FILE_NAME = "server_conf.properties";

    /**
     * Represent the value of PORT
     */
    public static final String PORT = "port";

    /**
     * Represent the value of USER
     */
    public static final String USER = "user";

    /**
     * Represent the value of PASSWORD
     */
    public static final String PASSWORD = "password";

    /**
     * Represent the value of MONIT_USER
     */
    public static final String MONIT_USER = "monit_user";

    /**
     * Represent the value of MONIT_PASSWORD
     */
    public static final String MONIT_PASSWORD = "monit_password";

    /**
     * Represent the value of MONIT_INSTALED
     */
    public static final String MONIT_INSTALED = "monit_instaled";

    /**
     * Represent the value of MONIT_URL
     */
    public static final String MONIT_URL = "monit_url";

    /**
     * Represent the value of configuration file
     */
    private static PropertiesConfiguration configuration = new PropertiesConfiguration();

    /**
     * Validate if the file exist
     * @return boolean
     */
    public static boolean isExist(){

        File file = new File(DIR_NAME+File.separator+FILE_NAME);
        return (file.exists() && !file.isDirectory());

    }

    /**
     * Create a new configuration file
     * @return File
     * @throws IOException
     */
    public static void create() throws IOException, ConfigurationException {

        LOG.info("Creating new configuration file...");

        File dir = new File(DIR_NAME);

        if (dir.mkdir()){
            LOG.info("Directory is created!");
        }else{
            LOG.info("Directory already exists.");
        }

        File file = new File(DIR_NAME+File.separator+FILE_NAME);

        if (file.createNewFile()){
            LOG.info("File is created!");
            file.setReadable(Boolean.TRUE);
            file.setWritable(Boolean.TRUE);
        }else{
            LOG.info("File already exists.");
        }

        LOG.info("Add configuration content");

        PropertiesConfiguration newConfigurationFile = new PropertiesConfiguration();
        newConfigurationFile.setFile(file);
        newConfigurationFile.setHeader("# ***********************************\n# * CLOUD SERVER CONFIGURATION FILE *\n# * www.fermat.org                  *\n# ***********************************");

        newConfigurationFile.getLayout().setComment(PORT, "\n# * SERVER PORT");
        newConfigurationFile.addProperty(PORT, JettyEmbeddedAppServer.DEFAULT_PORT);

        newConfigurationFile.getLayout().setComment(USER, "\n# * MONITORIG USER");
        newConfigurationFile.addProperty(USER, "fermat");
        newConfigurationFile.addProperty(PASSWORD, "5e494e695571ede182fb62299373678158c752fb8d3c04104a46b7de139dab5e"); //fermat

        newConfigurationFile.getLayout().setComment(MONIT_INSTALED, "\n# * MONIT CONFIGURATION");
        newConfigurationFile.addProperty(MONIT_INSTALED, Boolean.FALSE);
        newConfigurationFile.addProperty(MONIT_URL, "http://localhost:2812/");

        newConfigurationFile.save();

        LOG.info("Setup configuration file is complete!");
        LOG.info("Configuration file path = "+file.getAbsolutePath());
    }

    /**
     * Load the content of the configuration file
     * @throws ConfigurationException
     */
    public static void load() throws ConfigurationException {
        LOG.info("Loading configuration...");
        configuration.setFileName(DIR_NAME+File.separator+FILE_NAME);
        configuration.load();
    }

    /**
     * Get the value of a properties
     *
     * @param property
     * @return String
     */
    public static String getValue(String property){
        return configuration.getString(property);
    }

    /**
     * Update the value of a property
     * @param property
     * @param value
     */
    public static void updateValue(String property, String value) throws ConfigurationException {
        configuration.setProperty(property, value);
        configuration.save();
    }

}
