package com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.Service;

import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.CantCheckResourcesException;
import com.bitdubai.fermat_pip_api.layer.pip_network_service.subapp_resources.SubAppResourcesManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.EventHandler;



/**
 * Created by loui on 17/02/15.
 */
public class BegunSubAppInstallationEventHandler implements EventHandler {
    SubAppResourcesManager subappResourcesManager;
    
    public void setSubAppResourcesManager(SubAppResourcesManager subappResourcesManager){
        this.subappResourcesManager = subappResourcesManager;
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {




        if (((Service) this.subappResourcesManager).getStatus() == ServiceStatus.STARTED) {

         //   try
         //   {
               // this.subappResourcesManager.checkResources();
        //    }
        //    catch (CantCheckResourcesException cantCheckResourcesException)
        //    {
                /**
                 * The main module could not handle this exception. Me neither. Will throw it again.
                 */

           //     throw cantCheckResourcesException;
          //  }
        }
        else
        {
            throw new CantCheckResourcesException("CAN'T CHECK SUBAPP RESOURCES:",null,"Error intalled subapp resources fields" , "");
        }

    }
}
