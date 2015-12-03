package com.bitdubai.fermat_cbp_core.layer.negotiation_transaction;

import com.bitdubai.fermat_api.layer.all_definition.common.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cbp_core.layer.negotiation_transaction.customer_broker_close.CustomerBrokerClosePluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.negotiation_transaction.customer_broker_new.CustomerBrokerNewPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.negotiation_transaction.customer_broker_update.CustomerBrokerUpdatePluginSubsystem;

/**
 * Created by Yordin Alayn on 22.11/15.
 */
public class NegotiationTransactionLayer extends AbstractLayer {

    public NegotiationTransactionLayer() {
        super(Layers.NEGOTIATION_TRANSACTION);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new CustomerBrokerNewPluginSubsystem());
            registerPlugin(new CustomerBrokerUpdatePluginSubsystem());
            registerPlugin(new CustomerBrokerClosePluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }

}
