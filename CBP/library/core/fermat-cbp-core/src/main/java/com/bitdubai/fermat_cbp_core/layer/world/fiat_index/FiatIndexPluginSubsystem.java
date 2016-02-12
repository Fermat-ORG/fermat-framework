package com.bitdubai.fermat_cbp_core.layer.world.fiat_index;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cbp_plugin.layer.world.fiat_index.developer.bitdubai.DeveloperBitDubai;


/**
 * Created by Alejandro Bicelis on 11/27/2015.
 */
public class FiatIndexPluginSubsystem extends AbstractPluginSubsystem {

    public FiatIndexPluginSubsystem() {
        super(new PluginReference(Plugins.FIAT_INDEX));
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
