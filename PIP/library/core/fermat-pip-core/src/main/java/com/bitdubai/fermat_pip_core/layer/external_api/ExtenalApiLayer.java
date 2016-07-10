package com.bitdubai.fermat_pip_core.layer.external_api;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_pip_core.layer.external_api.geolocation.GeolocationPluginSubsystem;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 04/06/16.
 */
public class ExtenalApiLayer extends AbstractLayer {

    public ExtenalApiLayer() {
        super(Layers.EXTERNAL_API);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new GeolocationPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
