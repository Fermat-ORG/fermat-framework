package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;

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
public class getCustomerOnlinePaymentRecordTest {
    private CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }
    /*@Test
    public void getCustomerOnlinePaymentRecord_Should_Run_Once() throws Exception{
        customerOnlinePaymentBusinessTransactionDao.getCustomerOnlinePaymentRecord(anyString());
        verify(customerOnlinePaymentBusinessTransactionDao,Mockito.times(1)).getCustomerOnlinePaymentRecord(anyString());
    }*/

    @Test(expected = Exception.class)
    public void getCustomerOnlinePaymentRecord_Should_Return_Exception() throws Exception{
        customerOnlinePaymentBusinessTransactionDao = new CustomerOnlinePaymentBusinessTransactionDao(null,null,null);
        customerOnlinePaymentBusinessTransactionDao.getCustomerOnlinePaymentRecord(null);
    }
}
