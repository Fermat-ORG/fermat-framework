package com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.layer._10_middleware.Middleware;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;

/**
 * Created by loui on 18/02/15.
 */
public class WalletResourceInstalledEventHandler implements EventHandler {
    Middleware middleware;

    public void setMiddleware ( Middleware middleware){
        this.middleware = middleware;
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        
    }
}
