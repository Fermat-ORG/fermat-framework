package com.bitdubai.fermat_cbp_core.layer.user_level_business_transaction;

import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cbp_core.layer.user_level_business_transaction.customer_broker_purchase.CustomerBrokerPurchaseSubsystem;
import com.bitdubai.fermat_cbp_core.layer.user_level_business_transaction.customer_broker_sale.CustomerBrokerSaleSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;

/**
 * Created by franklin on 11/12/15.
 */
public class UserLevelBusinessTransactionLayer extends AbstractLayer {
    public UserLevelBusinessTransactionLayer() {
        super(Layers.USER_LEVEL_BUSINESS_TRANSACTION);
    }

    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new CustomerBrokerPurchaseSubsystem());
            registerPlugin(new CustomerBrokerSaleSubsystem());
        } catch (CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }
    }
}
