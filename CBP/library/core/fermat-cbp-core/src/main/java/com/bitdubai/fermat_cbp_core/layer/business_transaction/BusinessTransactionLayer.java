package com.bitdubai.fermat_cbp_core.layer.business_transaction;

import com.bitdubai.fermat_cbp_core.layer.business_transaction.broker_ack_offline_payment.BrokerAckOfflinePaymentPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.business_transaction.broker_ack_online_payment.BrokerAckOnlinePaymentPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.business_transaction.broker_submit_offline_merchandise.BrokerSubmitOfflinePaymentPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.business_transaction.broker_submit_online_merchandise.BrokerSubmitOnlinePaymentPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.business_transaction.costomer_offline_payment.CustomerOfflinePaymentPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.business_transaction.customer_ack_onile_merchandise.CustomerAckOnlineMerchandiseSubsystem;
import com.bitdubai.fermat_cbp_core.layer.business_transaction.customer_online_payment.CustomerOnlinePaymentPluginSubsystem;
import com.bitdubai.fermat_core_api.layer.all_definition.system.abstract_classes.AbstractLayer;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantRegisterPluginException;
import com.bitdubai.fermat_core_api.layer.all_definition.system.exceptions.CantStartLayerException;
import com.bitdubai.fermat_api.layer.all_definition.enums.Layers;
import com.bitdubai.fermat_cbp_core.layer.business_transaction.close_conctract.CloseContractPluginSubsystem;
import com.bitdubai.fermat_cbp_core.layer.business_transaction.open_contract.OpenContractPluginSubsystem;

/**
 * Created by Manuel Perez (darkpriestrelative@gmail.com) on 26/11/15.
 */
public class BusinessTransactionLayer extends AbstractLayer {
    public BusinessTransactionLayer() {
        super(Layers.BUSINESS_TRANSACTION);
    }

    @Override
    public void start() throws CantStartLayerException {

        try {

            registerPlugin(new BrokerAckOfflinePaymentPluginSubsystem());
            registerPlugin(new BrokerAckOnlinePaymentPluginSubsystem());
            registerPlugin(new BrokerSubmitOfflinePaymentPluginSubsystem());
            registerPlugin(new BrokerSubmitOnlinePaymentPluginSubsystem());
            registerPlugin(new CustomerAckOnlineMerchandiseSubsystem());
            registerPlugin(new CustomerOfflinePaymentPluginSubsystem());
            registerPlugin(new CustomerOnlinePaymentPluginSubsystem());
            registerPlugin(new CloseContractPluginSubsystem());
            registerPlugin(new OpenContractPluginSubsystem());

        } catch(CantRegisterPluginException e) {

            throw new CantStartLayerException(
                    e,
                    "",
                    "Problem trying to register a plugin."
            );
        }

    }
}
