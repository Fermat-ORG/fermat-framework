package com.bitdubai.fermat_dmp_plugin.layer.engine.sub_app_runtime.developer.bitdubai.version_1.event_handlers;

import com.bitdubai.fermat_api.FermatException;
import com.bitdubai.fermat_api.layer.dmp_engine.sub_app_runtime.SubAppRuntimeManager;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventHandler;

/**
 * Created by loui on 18/02/15.
 */
public class WalletResourcesInstalledEventHandler implements FermatEventHandler {

    private final SubAppRuntimeManager subAppRuntimeManager;

    public WalletResourcesInstalledEventHandler(final SubAppRuntimeManager subAppRuntimeManager) {

        this.subAppRuntimeManager = subAppRuntimeManager;
    }

    @Override
    public void handleEvent(FermatEvent fermatEvent) throws FermatException {
        
    }
}
