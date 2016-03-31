package com.bitdubai.fermat_tky_core.layer.external_api;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_tky_core.layer.external_api.tokenly.TokenlyPluginSubsystem;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/03/16.
 */
public class ExternalApiLayer extends AbstractLayer {

    public ExternalApiLayer() {
        super(Layers.EXTERNAL_API);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new TokenlyPluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}