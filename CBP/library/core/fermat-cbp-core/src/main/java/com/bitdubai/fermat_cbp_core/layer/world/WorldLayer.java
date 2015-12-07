package com.bitdubai.fermat_cbp_core.layer.world;

import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cbp_core.layer.world.fiat_index.FiatIndexPluginSubsystem;

/**
 * Created by Alejandro Bicelis on 11/27/2015.
 */
public class WorldLayer extends AbstractLayer {

    public WorldLayer() {
        super(Layers.WORLD);
    }

    public void start() throws CantStartLayerException {

        try {
            registerPlugin(new FiatIndexPluginSubsystem());
        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}