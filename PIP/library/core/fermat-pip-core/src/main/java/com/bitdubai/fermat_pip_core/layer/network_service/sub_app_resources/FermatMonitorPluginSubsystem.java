package com.bitdubai.fermat_pip_core.layer.network_service.sub_app_resources;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_resources.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by root on 23/03/16.
 */
public class FermatMonitorPluginSubsystem {


    public SubAppResourcesPluginSubsystem() {
        super(new PluginReference(Plugins.SUB_APP_FERMAT_MONITOR));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new DeveloperBitDubai());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}
