package com.bitdubai.fermat_core.layer.dmp_middleware;

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.layer.CantStartLayerException;
import com.bitdubai.fermat_api.layer.PlatformLayer;
import com.bitdubai.fermat_api.layer.dmp_middleware.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.dmp_middleware.MiddlewareSubsystem;
import com.bitdubai.fermat_core.layer.dmp_middleware.app_runtime.AppRuntimeSubsystem;

/**
 * Created by ciencias on 30.12.14.
 * Modified by Leon Acosta (laion.cj91@gmail.com) on 28/08/2015.
 */
public class MiddlewareLayer implements PlatformLayer {

    private Plugin mAppRuntimePlugin;

    private Plugin mNotificationPlugin;


    @Override
    public void start() throws CantStartLayerException {

        mAppRuntimePlugin = getPlugin(new AppRuntimeSubsystem());

    }

    private Plugin getPlugin(MiddlewareSubsystem middlewareSubsystem) throws CantStartLayerException {
        try {
            middlewareSubsystem.start();
            return middlewareSubsystem.getPlugin();
        } catch (CantStartSubsystemException e) {
            throw new CantStartLayerException();
        }
    }

    public Plugin getAppRuntimePlugin() {
        return mAppRuntimePlugin;
    }

}
