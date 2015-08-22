package com.bitdubai.fermat_dmp_plugin.layer.engine.app_runtime.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubAppRuntimeManager;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.PlatformEvent;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.event_manager.interfaces.EventHandler;

/**
 * Created by loui on 18/02/15.
 */
public class WalletResourcesInstalledEventHandler implements EventHandler {
    SubAppRuntimeManager subAppRuntimeManager;

    public void setSubAppRuntimeManager(SubAppRuntimeManager subAppRuntimeManager){
        this.subAppRuntimeManager = subAppRuntimeManager;
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws FermatException {
        
    }
}
