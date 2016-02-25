package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 01/02/16.
 */
public class getCustomerOfflinePaymentRecordTest {
    private CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    /*@Test
    public void getCustomerOfflinePaymentRecord_Should_Run_Once() throws Exception{
        customerOfflinePaymentBusinessTransactionDao.getCustomerOfflinePaymentRecord(anyString());
        //verify(customerOfflinePaymentBusinessTransactionDao,Mockito.times(1)).getCustomerOfflinePaymentRecord(anyString());
    }*/

    @Test(expected = Exception.class)
    public void getCustomerOfflinePaymentRecord_Should_Return_Exception() throws Exception{
        customerOfflinePaymentBusinessTransactionDao = new CustomerOfflinePaymentBusinessTransactionDao(null,null,null);
        customerOfflinePaymentBusinessTransactionDao.getCustomerOfflinePaymentRecord(null);
    }
}
