package com.bitdubai.fermat_cht_core.layer.actor_network_service;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cht_core.layer.actor_network_service.chat.ActorNetworkServicePluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by José D. Vilchez A. (josvilchezalmera@gmail.com) on 05/04/16.
 */
public class ActorNetworkServiceLayer extends AbstractLayer {
    public ActorNetworkServiceLayer() {
        super(Layers.ACTOR_NETWORK_SERVICE);
    }

    @Override
    public void start() throws CantStartLayerException {
        try {

            registerPlugin(new ActorNetworkServicePluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }
}
