package com.bitdubai.fermat_core.layer._11_network_service.intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.layer._11_network_service.intra_user.IntraUserManager;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._3_platform_service.event_manager.EventHandler;

/**
 * Created by loui on 19/02/15.
 */
public class UserLoggedOutEventHandler implements EventHandler{
    IntraUserManager intraUserManager;

    public void setIntraUserManager(IntraUserManager intraUserManager){
        this.intraUserManager = intraUserManager;
        
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {

    }
}
