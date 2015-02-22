package com.bitdubai.fermat_core.layer._11_middleware.app_runtime.developer.bitdubai.version_1;

import com.bitdubai.fermat_api.layer._11_middleware.app_runtime.AppRuntimeManager;
import com.bitdubai.fermat_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer._2_platform_service.event_manager.EventHandler;

/**
 * Created by loui on 18/02/15.
 */
public class WalletResourcesInstalledEventHandler implements EventHandler {
    AppRuntimeManager appRuntimeManager;

    public void setAppRuntimeManager ( AppRuntimeManager appRuntimeManager){
        this.appRuntimeManager = appRuntimeManager;
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        
    }
}
