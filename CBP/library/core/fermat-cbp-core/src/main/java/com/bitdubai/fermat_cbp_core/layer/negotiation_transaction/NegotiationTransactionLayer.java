package com.bitdubai.fermat_cbp_core.layer.negotiation_transaction;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cbp_core.layer.actor_connection.crypto_broker.CryptoBrokerPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.actor_connection.crypto_customer.CryptoCustomerPluginSubsystem;

/**
 * Created by Yordin Alayn on 22.11/15.
 */
public class NegotiationTransactionLayer extends AbstractLayer {

    public NegotiationTransactionLayer() {
        super(Layers.TRANSACTION);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new CryptoBrokerPluginSubsystem());
            registerPlugin(new CryptoCustomerPluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
