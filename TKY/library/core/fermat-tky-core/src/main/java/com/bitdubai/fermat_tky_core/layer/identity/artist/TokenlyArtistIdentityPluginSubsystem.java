package com.bitdubai.fermat_tky_core.layer.identity.artist;

import com.bitdubai.fermat_api.layer.all_definition.common.system.utils.PluginReference;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartSubsystemException;
import com.bitdubai.fermat_tky_plugin.layer.identity.artist_identity.developer.bitdubai.DeveloperBitDubai;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/03/16.
 */
public class TokenlyArtistIdentityPluginSubsystem extends AbstractPluginSubsystem {

    public TokenlyArtistIdentityPluginSubsystem() {
        super(new PluginReference(Plugins.TOKENLY_ARTIST));
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