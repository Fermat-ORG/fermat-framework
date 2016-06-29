package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.Service;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;
import com.bitdubai.fermat_pip_api.layer.network_service.CantCheckResourcesException;
import com.bitdubai.fermat_pip_api.layer.network_service.subapp_resources.SubAppResourcesInstalationManager;


/**
 * Created by loui on 17/02/15.
 */
public class BegunSubAppInstallationEventHandler implements FermatEventHandler {
    SubAppResourcesInstalationManager subappResourcesInstalationManager;

    public void setSubAppResourcesManager(SubAppResourcesInstalationManager subappResourcesInstalationManager) {
        this.subappResourcesInstalationManager = subappResourcesInstalationManager;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {


        if (((Service) this.subappResourcesInstalationManager).getStatus() == ServiceStatus.STARTED) {

            //   try
            //   {
            // this.subappResourcesInstalationManager.checkResources();
            //    }
            //    catch (CantCheckResourcesException cantCheckResourcesException)
            //    {
            /**
             * The main module could not handle this exception. Me neither. Will throw it again.
             */

            //     throw cantCheckResourcesException;
            //  }
        } else {
            throw new CantCheckResourcesException("CAN'T CHECK SUBAPP RESOURCES:", null, "Error intalled subapp resources fields", "");
        }

    }
}
