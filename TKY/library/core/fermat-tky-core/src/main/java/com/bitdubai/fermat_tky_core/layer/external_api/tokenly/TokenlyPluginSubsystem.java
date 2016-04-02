package com.bitdubai.fermat_tky_core.layer.external_api.tokenly;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_tky_plugin.layer.external_api.tokenly.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 11/03/16.
 */
public class TokenlyPluginSubsystem extends AbstractPluginSubsystem {

    public TokenlyPluginSubsystem() {
        super(new PluginReference(Plugins.TOKENLY_API));
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
