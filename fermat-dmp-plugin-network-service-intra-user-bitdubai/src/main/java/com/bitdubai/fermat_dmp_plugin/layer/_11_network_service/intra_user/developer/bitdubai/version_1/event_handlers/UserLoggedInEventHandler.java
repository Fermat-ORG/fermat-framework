/*
 * @#UserLoggedInEventHandler.java - 2015
 * Copyright bitDubai.com., All rights reserved.
 * You may not modify, use, reproduce or distribute this software.
 * BITDUBAI/CONFIDENTIAL
 */
package com.bitdubai.fermat_dmp_plugin.layer._11_network_service.intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._11_network_service.intra_user.IntraUserManager;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.events.IntraUserLoggedInEvent;
import com.bitdubai.fermat_dmp_plugin.layer._11_network_service.intra_user.developer.bitdubai.version_1.IntraUserNetworkServicePluginRoot;

/**
 * The Class <code>com.bitdubai.fermat_dmp_plugin.layer._11_network_service.intra_user.developer.bitdubai.version_1.structure.IntraUserNetworkServiceDao</code> have
 * logic implementation to handle the event <code>com.bitdubai.fermat_api.layer.platform_service.event_manager.events.IntraUserLoggedInEvent</code>
 * <p/>
 *
 * Created by loui on 19/02/15.
 * Update by Roberto Requena - (rart3001@gmail.com) on 31/05/15.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class UserLoggedInEventHandler implements EventHandler{

    /*
     * Represent the intraUserManager
     */
    private IntraUserManager intraUserManager;


    /**
     * Constructor with parameter
     *
     * @param intraUserManager
     */
    public UserLoggedInEventHandler(IntraUserManager intraUserManager) {
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
    public void handleEvent(PlatformEvent platformEvent) throws Exception {

        if (((Service) this.intraUserManager).getStatus() == ServiceStatus.STARTED) {

            /*
             *  IntraUserManager make the job
             */
            ((IntraUserNetworkServicePluginRoot) this.intraUserManager).logIn(((IntraUserLoggedInEvent) platformEvent).getIntraUserId());

        }
    }
}
