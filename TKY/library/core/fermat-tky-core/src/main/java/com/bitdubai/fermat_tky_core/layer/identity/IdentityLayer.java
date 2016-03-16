package com.bitdubai.fermat_tky_core.layer.identity;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_tky_core.layer.identity.artist.TokenlyArtistIdentityPluginSubsystem;

/**
 * Created by Gabriel Araujo (gabe_512@hotmail.com) on 08/03/16.
 */
public class IdentityLayer extends AbstractLayer {

    public IdentityLayer() {
        super(Layers.IDENTITY);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new TokenlyArtistIdentityPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}