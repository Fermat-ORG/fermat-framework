package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.CustomerOfflinePaymentPluginRootTest;

import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.CustomerOfflinePaymentPluginRoot;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 28/01/16.
 */
public class testGetClassesFullPath {
    CustomerOfflinePaymentPluginRoot customerOfflinePaymentPluginRoot = new CustomerOfflinePaymentPluginRoot();

    @Test
    public void GetClassesFullPath_Should_Return_List() throws Exception {
        assertNotNull(customerOfflinePaymentPluginRoot.getClassesFullPath());
    }

}

