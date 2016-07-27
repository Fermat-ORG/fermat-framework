/*
 * @#ConfigurationManager  - 2016
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util;


import com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.FermatEmbeddedNodeServer;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.communications.network.node.developer.bitdubai.version_1.structure.util.ConfigurationManager</code> implements
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
    public static final String DIR_NAME = getExternalStorageDirectory()+"/configuration";

    /**
     * Represent the value of FILE_NAME
     */
    public static final String FILE_NAME = "server_conf.properties";

    /**
     * Represent the value of IDENTITY_PUBLIC_KEY
     */
    public static final String IDENTITY_PUBLIC_KEY = "ipk";

    /**
     * Represent the value of NODE_NAME
     */
    public static final String NODE_NAME = "node_name";

    /**
     * Represent the value of LAST_REGISTER_NODE_PROFILE
     */
    public static final String LAST_REGISTER_NODE_PROFILE = "last_register_node_profile";

    /**
     * Represent the value of INTERNAL_IP
     */
    public static final String INTERNAL_IP = "internal_ip";

    /**
     * Represent the value of INTERNAL_IP
     */
    public static final String PUBLIC_IP = "public_ip";

    /**
     * Represent the value of LATITUDE
     */
    public static final String LATITUDE = "latitude";

    /**
     * Represent the value of LONGITUDE
     */
    public static final String LONGITUDE = "longitude";

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
     * Represent the value of REGISTERED_IN_CATALOG
     */
    public static final String REGISTERED_IN_CATALOG = "register_in_catalog";

    /**
     * Represent the value of MONIT_USER
     */
    public static final String MONIT_USER = "monit_user";

    /**
     * Represent the value of MONIT_PASSWORD
     */
    public static final String MONIT_PASSWORD = "monit_password";

    /**
     * Represent the value of MONIT_INSTALLED
     */
    public static final String MONIT_INSTALLED = "monit_installed";

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
    public static void create(String identityPublicKey) throws IOException, ConfigurationException {

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
        newConfigurationFile.setHeader("# ***********************************\n# * NETWORK NODE CONFIGURATION FILE (V2) *\n# * www.fermat.org                  *\n# ***********************************");

        newConfigurationFile.getLayout().setComment(IDENTITY_PUBLIC_KEY, "\n# * SERVER IDENTITY PUBLIC KEY");
        newConfigurationFile.addProperty(IDENTITY_PUBLIC_KEY, identityPublicKey);

        newConfigurationFile.getLayout().setComment(NODE_NAME, "\n# * NODE NAME");
        newConfigurationFile.addProperty(NODE_NAME, "Fermat Node (" + InetAddress.getLocalHost().getHostName() + ")");

        newConfigurationFile.getLayout().setComment(INTERNAL_IP, "\n# * SERVER INTERNAL IP (Configure 0.0.0.0 to server listen to all network interfaces)");
        newConfigurationFile.addProperty(INTERNAL_IP, FermatEmbeddedNodeServer.DEFAULT_IP);

        newConfigurationFile.getLayout().setComment(PUBLIC_IP, "\n# * SERVER PUBLIC IP (Configure 0.0.0.0 by default)");
        newConfigurationFile.addProperty(PUBLIC_IP, FermatEmbeddedNodeServer.DEFAULT_IP);

        newConfigurationFile.getLayout().setComment(PORT, "\n# * SERVER PORT");
        newConfigurationFile.addProperty(PORT, FermatEmbeddedNodeServer.DEFAULT_PORT);

        newConfigurationFile.getLayout().setComment(LATITUDE, "\n# * SERVER LOCATION");
        newConfigurationFile.addProperty(LATITUDE,  "0.0");
        newConfigurationFile.addProperty(LONGITUDE, "0.0");

        newConfigurationFile.getLayout().setComment(REGISTERED_IN_CATALOG, "\n# * IS THE NODE REGISTER IN THE CATALOG");
        newConfigurationFile.addProperty(REGISTERED_IN_CATALOG, Boolean.FALSE);

        newConfigurationFile.getLayout().setComment(LAST_REGISTER_NODE_PROFILE, "\n# * THE LAST REGISTER NODE PROFILE IN THE NODES CATALOG (DON'T MODIFY MANUALLY)");
        newConfigurationFile.addProperty(LAST_REGISTER_NODE_PROFILE, "{}");

        newConfigurationFile.getLayout().setComment(USER, "\n# * MONITORIG USER");
        newConfigurationFile.addProperty(USER, "fermat");
        newConfigurationFile.addProperty(PASSWORD, "5e494e695571ede182fb62299373678158c752fb8d3c04104a46b7de139dab5e"); //fermat

        newConfigurationFile.getLayout().setComment(MONIT_INSTALLED, "\n# * MONIT CONFIGURATION");
        newConfigurationFile.addProperty(MONIT_INSTALLED, Boolean.FALSE);
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

    /**
     * Get the path to file system folder
     * @return String path to file folder
     **/
    private static String getExternalStorageDirectory() {

        //User home directory
        String home = System.getProperty("user.home");
        File dir = new File(home+"/externalStorage/files/");
        dir.mkdirs();
        return dir.getAbsolutePath();

    }

}
