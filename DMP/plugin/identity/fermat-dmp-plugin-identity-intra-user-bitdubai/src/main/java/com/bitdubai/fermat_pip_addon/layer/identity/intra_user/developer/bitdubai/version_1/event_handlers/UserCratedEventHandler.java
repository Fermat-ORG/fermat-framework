package com.bitdubai.fermat_pip_addon.layer.user.intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer._1_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.events.DeviceUserCreatedEvent;
import com.bitdubai.fermat_api.layer.pip_user.intra_user.IntraUserManager;
import com.bitdubai.fermat_api.layer.pip_user.intra_user.exceptions.CantCreateIntraUserException;

import java.util.UUID;

/**
 * Created by loui on 22/02/15.
 */
public class UserCratedEventHandler implements EventHandler {
    IntraUserManager intraUserManager;






    public void setIntraUserManager(IntraUserManager intraUserManager) {
        this.intraUserManager = intraUserManager;
    }







    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        UUID userId = ((DeviceUserCreatedEvent)platformEvent).getUserId();


        if (((Service) this.intraUserManager).getStatus() == ServiceStatus.STARTED) {

            try
            {
                this.intraUserManager.crateUser(userId);
            }
            catch (CantCreateIntraUserException cantCreateIntraUserException)
            {
                System.err.println("CantCreateIntraUserException: " + cantCreateIntraUserException.getMessage());
                cantCreateIntraUserException.printStackTrace();

                throw cantCreateIntraUserException;

            }

        }
    }
}
