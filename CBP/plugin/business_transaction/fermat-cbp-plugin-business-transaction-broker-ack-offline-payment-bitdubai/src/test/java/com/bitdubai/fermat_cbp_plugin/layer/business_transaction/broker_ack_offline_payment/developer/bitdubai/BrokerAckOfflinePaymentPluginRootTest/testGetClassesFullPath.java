package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.BrokerAckOfflinePaymentPluginRootTest;

import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.BrokerAckOfflinePaymentPluginRoot;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 17/02/16.
 */
public class testGetClassesFullPath {
    BrokerAckOfflinePaymentPluginRoot brokerAckOfflinePaymentPluginRoot = new BrokerAckOfflinePaymentPluginRoot();


    @Test
    public void GetClassesFullPath_Should_Return_List() throws Exception {
        assertNotNull(brokerAckOfflinePaymentPluginRoot.getClassesFullPath());
    }


}//main
