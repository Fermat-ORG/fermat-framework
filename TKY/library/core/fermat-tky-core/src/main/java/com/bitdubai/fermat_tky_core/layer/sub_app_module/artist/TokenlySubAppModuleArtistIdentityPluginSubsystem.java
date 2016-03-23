package com.bitdubai.fermat_tky_core.layer.sub_app_module.artist;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_tky_plugin.layer.sub_app_module.artist_identity.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Alexander Jimenez (alex_jimenez76@hotmail.com) on 3/18/16.
 */
public class TokenlySubAppModuleArtistIdentityPluginSubsystem extends AbstractPluginSubsystem {
    public TokenlySubAppModuleArtistIdentityPluginSubsystem() {
        super(new PluginReference(Plugins.TOKENLY_ARTIST_SUB_APP_MODULE));
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
