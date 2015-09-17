/*
 * @#UserLoggedOutEventHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.dmp_network_service.intra_user.interfaces.IntraUserManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer.network_service.intra_user.developer.bitdubai.version_1.event_handlers.UserLoggedOutEventHandler</code> have
 * logic implementation to handle de user logged out event
 * <p/>
 *
 * Created by loui on 19/02/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class UserLoggedOutEventHandler implements EventHandler{

    /*
     * Represent the intraUserManager
     */
    private IntraUserManager intraUserManager;


    /**
     * Constructor with parameter
     *
     * @param intraUserManager
     */
    public UserLoggedOutEventHandler(IntraUserManager intraUserManager) {
        this.intraUserManager = intraUserManager;
    }


    /**
     * (non-Javadoc)
     *
     * @see EventHandler#handleEvent(PlatformEvent)
     *
     * @param platformEvent
     * @throws Exception
     */
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException {

        if (((Service) this.intraUserManager).getStatus() == ServiceStatus.STARTED) {

            /*
             *  ActorIntraUserManager make the job
             */
            //((IntraUserNetworkServicePluginRoot) this.intraUserManager).logIn(((IntraUserLoggedOutEvent) platformEvent).getIntraUserId());

        }

    }
}
