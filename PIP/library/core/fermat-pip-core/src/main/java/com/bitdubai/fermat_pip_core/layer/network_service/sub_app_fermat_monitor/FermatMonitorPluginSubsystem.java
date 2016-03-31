package com.bitdubai.fermat_pip_core.layer.network_service.sub_app_fermat_monitor;


import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_pip_plugin.layer.network_service.subapp_fermat_monitor.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by root on 23/03/16.
 */
public class FermatMonitorPluginSubsystem extends AbstractPluginSubsystem {


    public FermatMonitorPluginSubsystem() {
        super(new PluginReference(Plugins.PIP_FERMAT_MONITOR));
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
