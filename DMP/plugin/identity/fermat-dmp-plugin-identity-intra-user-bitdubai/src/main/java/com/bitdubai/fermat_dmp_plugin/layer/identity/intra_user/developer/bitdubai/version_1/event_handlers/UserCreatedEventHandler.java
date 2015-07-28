package com.bitdubai.fermat_dmp_plugin.layer.identity.intra_user.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventHandler;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.events.DeviceUserCreatedEvent;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.IntraUserManager;
import com.bitdubai.fermat_api.layer.dmp_actor.intra_user.exceptions.CantCreateIntraUserException;

/**
 * Created by loui on 22/02/15.
 */
public class UserCreatedEventHandler implements EventHandler {
    IntraUserManager intraUserManager;


    public void setIntraUserManager(IntraUserManager intraUserManager) {
        this.intraUserManager = intraUserManager;
    }


    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        // TODO CHANGED THE RETURNING OF UUID OF THE DEVICE USER TO ITS PUBLIC KEY
        String userPublicKey = ((DeviceUserCreatedEvent) platformEvent).getPublicKey();


        if (((Service) this.intraUserManager).getStatus() == ServiceStatus.STARTED) {

            try {
                this.intraUserManager.createActor(userPublicKey);
            } catch (CantCreateIntraUserException cantCreateIntraUserException) {
                System.err.println("CantCreateIntraUserException: " + cantCreateIntraUserException.getMessage());
                cantCreateIntraUserException.printStackTrace();

                throw cantCreateIntraUserException;

            }

        }
    }
}
