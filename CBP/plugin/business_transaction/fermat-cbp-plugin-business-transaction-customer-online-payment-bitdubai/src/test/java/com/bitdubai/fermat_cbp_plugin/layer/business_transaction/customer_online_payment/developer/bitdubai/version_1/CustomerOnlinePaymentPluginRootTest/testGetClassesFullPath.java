package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.CustomerOnlinePaymentPluginRootTest;

import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.CustomerOnlinePaymentPluginRoot;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 28/01/16.
 */
public class testGetClassesFullPath {
    CustomerOnlinePaymentPluginRoot customerOnlinePaymentPluginRoot = new CustomerOnlinePaymentPluginRoot();

    @Test
    public void GetClassesFullPath_Should_Return_String() throws Exception {
        assertEquals(customerOnlinePaymentPluginRoot.getClassesFullPath().get(0),
                "com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.CustomerOnlinePaymentPluginRoot");
    }

}
