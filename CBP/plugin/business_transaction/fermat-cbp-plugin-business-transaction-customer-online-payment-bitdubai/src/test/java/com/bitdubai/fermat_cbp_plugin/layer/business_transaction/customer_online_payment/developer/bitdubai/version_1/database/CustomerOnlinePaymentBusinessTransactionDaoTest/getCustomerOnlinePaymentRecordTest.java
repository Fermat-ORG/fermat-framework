package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;

import org.junit.Test;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 31/01/16.
 */
public class getCustomerOnlinePaymentRecordTest {
    private CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;

    @Test(expected = Exception.class)
    public void isContractHashInDatabase_Should_Return_Exception() throws Exception{
        customerOnlinePaymentBusinessTransactionDao.getCustomerOnlinePaymentRecord(null);
    }
}
