package com.bitdubai.fermat_art_core.layer.identity;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_art_core.layer.identity.artist.ArtistIdentityPluginSubsystem;
import com.bitdubai.fermat_art_core.layer.identity.fanatic.FanaticIdentityPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/03/16.
 */
public class IdentityLayer extends AbstractLayer {

    public IdentityLayer() {
        super(Layers.IDENTITY);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new ArtistIdentityPluginSubsystem());
            registerPlugin(new FanaticIdentityPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}