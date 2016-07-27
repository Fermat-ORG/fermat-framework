package com.bitdubai.fermat_cer_core.layer.provider.bitfinex;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_cer_plugin.layer.provider.bitfinex.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;


/**
 * Created by Alejandro Bicelis on 18/03/2016.
 */
public class BitfinexSubsystem extends AbstractPluginSubsystem {

    public BitfinexSubsystem() {
        super(new PluginReference(Plugins.BITFINEX));
    }

    @Override
    public void start() throws CantStartSubsystemException {
        try {
            registerDeveloper(new DeveloperBitDubai());
        } catch (Exception e) {
            System.err.println(new StringBuilder().append("Exception: ").append(e.getMessage()).toString());
            throw new CantStartSubsystemException(e, null, null);
        }
    }
}




