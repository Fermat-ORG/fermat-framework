/*
 * @#WsCommunicationsServerCloudPluginRoot.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.FermatManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.JettyEmbeddedAppServer;
import com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.structure.jetty.util.ConfigurationManager;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.lang.ClassUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;


/**
 * The Class <code>com.bitdubai.fermat_p2p_plugin.layer.ws.communications.cloud.server.developer.bitdubai.version_1.WsCommunicationsServerCloudPluginRoot</code> is
 * the responsible to initialize all component to work together, and hold all resources they needed.
 * <p/>
 *
 * Created by Roberto Requena - (rart3001@gmail.com) on 03/09/15.
 *
 * @version 1.0
 */
public class WsCommunicationsServerCloudPluginRoot implements Service, Plugin {

    /**
     * Represent the logger instance
     */
    private Logger LOG = Logger.getLogger(ClassUtils.getShortClassName(WsCommunicationsServerCloudPluginRoot.class));

    /**
     * Represents the value of DISABLE_SERVER
     */
    public static final Boolean DISABLE_SERVER = Boolean.TRUE;

    /**
     * Represents the value of ENABLE_SERVER
     */
    public static final Boolean ENABLE_SERVER = Boolean.FALSE;

    /**
     * Represent the status of this service
     */
    private ServiceStatus serviceStatus = ServiceStatus.CREATED;

    /**
     * DealsWithPluginIdentity Interface member variables.
     */
    private UUID pluginId;

    /**
     * Represent the disableServerFlag
     */
    private Boolean disableServerFlag;

    /**
     * Constructor
     */
    public WsCommunicationsServerCloudPluginRoot(){
        super();
        this.disableServerFlag = WsCommunicationsServerCloudPluginRoot.DISABLE_SERVER;
    }

    @Override
    public FermatManager getManager() {
        return null;
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#start()
     */
    @Override
    public void start() {

        try {

            /*
             * Validate required resources
             */
           // validateInjectedResources();

            if (disableServerFlag) {
                LOG.info("Local Server is Disable, no started");
                return;
            }

            LOG.info("Starting plugin");

            /*
             * Initialize the configuration file
             */
            initializeConfigurationFile();

            /*
             * Start the server
             */
            JettyEmbeddedAppServer.getInstance().start();

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        /*
         * Set the new status of the service
         */
        this.serviceStatus = ServiceStatus.STARTED;

    }

    /**
     * Initialize the configuration file
     */
    private void initializeConfigurationFile() throws ConfigurationException, IOException {

        LOG.info("Starting initializeConfigurationFile()");

        if(ConfigurationManager.isExist()){

            ConfigurationManager.load();

        }else {

            LOG.info("Configuration file don't exit");
            ConfigurationManager.create();
            ConfigurationManager.load();
        }

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#pause()
     */
    @Override
    public void pause() {
        this.serviceStatus = ServiceStatus.PAUSED;
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#resume()
     */
    @Override
    public void resume() {
        this.serviceStatus = ServiceStatus.STARTED;
    }

    /**
     * (non-Javadoc)
     *
     * @see Service#stop()
     */
    @Override
    public void stop() {

        /*
         * Change the status
         */
        this.serviceStatus = ServiceStatus.STOPPED;

    }

    /**
     * (non-Javadoc)
     *
     * @see Service#getStatus()
     */
    @Override
    public ServiceStatus getStatus() {
        return this.serviceStatus;
    }

    /**
     * (non-Javadoc)
     *
     * @see Plugin#setId(UUID)
     */
    @Override
    public void setId(UUID pluginId) {
       this.pluginId = pluginId;
    }

    /**
     * Get the disable server flag
     *
     * @return Boolean
     */
    public Boolean getDisableServerFlag() {
        return disableServerFlag;
    }

    /**
     * Set Disable Server Flag
     *
     * @param disableServerFlag
     */
    public void setDisableServerFlag(Boolean disableServerFlag) {
        this.disableServerFlag = disableServerFlag;
    }
}
