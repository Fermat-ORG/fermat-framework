package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.structure.CustomerOfflinePaymentRecordTest;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.structure.CustomerOfflinePaymentRecord;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 16/02/16.
 */
public class GetSetTest {
    CustomerOfflinePaymentRecord customerOfflinePaymentRecord;

    @Test
    public void getSetTest() {
        customerOfflinePaymentRecord = new CustomerOfflinePaymentRecord();
        customerOfflinePaymentRecord.setBrokerPublicKey("Test1");
        customerOfflinePaymentRecord.setContractHash("Test2");
        customerOfflinePaymentRecord.setContractTransactionStatus(ContractTransactionStatus.CONFIRM_OFFLINE_PAYMENT);
        customerOfflinePaymentRecord.setCustomerPublicKey("Test3");
        customerOfflinePaymentRecord.setTimestamp(1);
        customerOfflinePaymentRecord.setTransactionHash("Test4");
        customerOfflinePaymentRecord.setTransactionId("Test5");

        assertEquals(customerOfflinePaymentRecord.getBrokerPublicKey(), "Test1");
        assertEquals(customerOfflinePaymentRecord.getContractHash(), "Test2");
        assertEquals(customerOfflinePaymentRecord.getContractTransactionStatus(), ContractTransactionStatus.CONFIRM_OFFLINE_PAYMENT);
        assertEquals(customerOfflinePaymentRecord.getCustomerPublicKey(), "Test3");
        assertEquals(customerOfflinePaymentRecord.getTimestamp(), 1);
        assertEquals(customerOfflinePaymentRecord.getTransactionHash(), "Test4");
        assertEquals(customerOfflinePaymentRecord.getTransactionId(), "Test5");
    }
}
