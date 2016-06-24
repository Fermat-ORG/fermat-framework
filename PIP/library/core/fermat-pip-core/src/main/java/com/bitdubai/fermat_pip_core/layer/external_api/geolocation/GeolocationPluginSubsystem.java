package com.bitdubai.fermat_pip_core.layer.external_api.geolocation;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

import org.fermat.fermat_pip_plugin.layer.external_api.geolocation.developer.DeveloperBitDubai;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/06/16.
 */
public class GeolocationPluginSubsystem extends AbstractPluginSubsystem {

    public GeolocationPluginSubsystem() {
        super(new PluginReference(Plugins.GEOLOCATION));
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

