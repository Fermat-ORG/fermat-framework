package com.bitdubai.wallet_platform_core.layer._10_middleware.app_runtime.developer.bitdubai.version_1;

import com.bitdubai.wallet_platform_api.layer._10_middleware.MiddlewareEngine;
import com.bitdubai.wallet_platform_api.layer._1_definition.event.PlatformEvent;
import com.bitdubai.wallet_platform_api.layer._2_platform_service.event_manager.EventHandler;

/**
 * Created by loui on 18/02/15.
 */
public class WalletResourceInstalledEventHandler implements EventHandler {
    MiddlewareEngine middlewareEngine;

    public void setMiddleware ( MiddlewareEngine middleware){
        this.middlewareEngine = middleware;
    }
    
    @Override
    public void handleEvent(PlatformEvent platformEvent) throws Exception {
        
    }
}
