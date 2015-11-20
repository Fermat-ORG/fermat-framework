package com.bitdubai.fermat_ccp_core.layer.network_service;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_ccp_core.layer.network_service.crypto_addresses.CryptoAddressesPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.network_service.crypto_payment_request.CryptoPaymentRequestPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.network_service.crypto_transmission.CryptoTransmissionPluginSubsystem;
import com.bitdubai.fermat_ccp_core.layer.network_service.intra_user.IntraUserPluginSubsystem;

/**
 * Created by Leon Acosta - (laion.cj91@gmail.com) on 22/09/2015.
 *
 * @version 1.0
 * @since Java JDK 1.7
 */
public class NetworkServiceLayer extends AbstractLayer {

    public NetworkServiceLayer() {
        super(Layers.NETWORK_SERVICE);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new CryptoAddressesPluginSubsystem());
            registerPlugin(new CryptoPaymentRequestPluginSubsystem());
            registerPlugin(new CryptoTransmissionPluginSubsystem());
            registerPlugin(new IntraUserPluginSubsystem());


        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
