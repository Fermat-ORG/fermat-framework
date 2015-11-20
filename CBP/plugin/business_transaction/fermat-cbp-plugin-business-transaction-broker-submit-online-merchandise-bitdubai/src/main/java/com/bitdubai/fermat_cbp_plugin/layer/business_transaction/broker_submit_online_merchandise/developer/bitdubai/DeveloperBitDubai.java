<<<<<<< HEAD:CBP/plugin/business_transaction/fermat-cbp-plugin-business-transaction-ack-payment-bitdubai/src/main/java/com/bitdubai/fermat_cbp_plugin/layer/business_transaction/ack_payment/developer/bitdubai/DeveloperBitDubai.java
package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.ack_payment.developer.bitdubai;
=======
package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai;
>>>>>>> 7875b05aa57a6363a48d3097d1c6f69bda9f1d37:CBP/plugin/business_transaction/fermat-cbp-plugin-business-transaction-broker-submit-online-merchandise-bitdubai/src/main/java/com/bitdubai/fermat_cbp_plugin/layer/business_transaction/broker_submit_online_merchandise/developer/bitdubai/DeveloperBitDubai.java

import com.bitdubai.fermat_api.Plugin;
import com.bitdubai.fermat_api.PluginDeveloper;
import com.bitdubai.fermat_api.layer.all_definition.enums.CryptoCurrency;
import com.bitdubai.fermat_api.layer.all_definition.enums.TimeFrequency;
import com.bitdubai.fermat_api.layer.all_definition.license.PluginLicensor;
<<<<<<< HEAD:CBP/plugin/business_transaction/fermat-cbp-plugin-business-transaction-ack-payment-bitdubai/src/main/java/com/bitdubai/fermat_cbp_plugin/layer/business_transaction/ack_payment/developer/bitdubai/DeveloperBitDubai.java
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.ack_payment.developer.bitdubai.version_1.BusinessTransactionCustomerAckPaymentPluginRoot;
=======
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_submit_online_merchandise.developer.bitdubai.version_1.BrokerSubmitOnlineMerchandisePluginRoot;
>>>>>>> 7875b05aa57a6363a48d3097d1c6f69bda9f1d37:CBP/plugin/business_transaction/fermat-cbp-plugin-business-transaction-broker-submit-online-merchandise-bitdubai/src/main/java/com/bitdubai/fermat_cbp_plugin/layer/business_transaction/broker_submit_online_merchandise/developer/bitdubai/DeveloperBitDubai.java

/**
 * Created by Yordin Alayn on 16.09.15.
 */

public class DeveloperBitDubai implements PluginDeveloper, PluginLicensor {

    Plugin plugin;

    @Override
    public Plugin getPlugin() {
        return plugin;
    }

    public DeveloperBitDubai () {
        plugin = new BrokerSubmitOnlineMerchandisePluginRoot();
    }

    @Override
    public int getAmountToPay() {
        return 100;
    }

    @Override
    public CryptoCurrency getCryptoCurrency() {
        return CryptoCurrency.BITCOIN;
    }

    @Override
    public String getAddress() {
        return "13gpMizSNvQCbJzAPyGCUnfUGqFD8ryzcv";
    }

    @Override
    public TimeFrequency getTimePeriod() {
        return TimeFrequency.MONTHLY;
    }

}
