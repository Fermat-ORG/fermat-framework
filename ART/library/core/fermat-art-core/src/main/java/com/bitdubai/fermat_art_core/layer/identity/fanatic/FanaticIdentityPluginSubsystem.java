package com.bitdubai.fermat_art_core.layer.identity.fanatic;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_art_plugin.layer.identity.fan.developer.bitdubai.DeveloperBitDubai;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/03/16.
 */
public class FanaticIdentityPluginSubsystem extends AbstractPluginSubsystem {

    public FanaticIdentityPluginSubsystem() {
        super(new PluginReference(Plugins.FANATIC_IDENTITY));
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