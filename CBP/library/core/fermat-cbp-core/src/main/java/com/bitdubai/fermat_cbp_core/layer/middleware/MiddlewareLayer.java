package com.bitdubai.fermat_cbp_core.layer.middleware;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cbp_core.layer.middleware.matching_engine.MatchingEnginePluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 09/02/2016.
 *
 * @author lnacosta
 * @version 1.0
 * @since Java JDK 1.7
 */
public class MiddlewareLayer extends AbstractLayer {

    public MiddlewareLayer() {
        super(Layers.MIDDLEWARE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new MatchingEnginePluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "Middleware CBP Layer",
                    "Problem trying to register a plugin."
            );
        }
    }

}
