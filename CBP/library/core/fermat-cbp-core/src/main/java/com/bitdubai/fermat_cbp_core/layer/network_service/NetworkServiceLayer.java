package com.bitdubai.fermat_cbp_core.layer.network_service;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cbp_core.layer.network_service.negotiation_transmission.NegotiationTransmissionPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.network_service.transaction_transmission.TransactionTransmissionPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 22/11/15.
 */
public class NetworkServiceLayer extends AbstractLayer {

    public NetworkServiceLayer() {
        super(Layers.NETWORK_SERVICE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new TransactionTransmissionPluginSubsystem());
            registerPlugin(new NegotiationTransmissionPluginSubsystem());

        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
