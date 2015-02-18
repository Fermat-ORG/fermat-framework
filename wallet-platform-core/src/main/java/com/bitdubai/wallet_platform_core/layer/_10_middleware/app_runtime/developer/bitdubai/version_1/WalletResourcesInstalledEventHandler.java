package com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.layer._10_middleware.app_runtime.AppRuntimeManager;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;

/**
 * Created by loui on 18/02/15.
 */
public class WalletResourcesInstalledEventHandler implements EventHandler {
    AppRuntimeManager appRuntimeManager;

    public void setMiddleware ( AppRuntimeManager appRuntimeManager){
        this.appRuntimeManager = appRuntimeManager;
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        
    }
}
