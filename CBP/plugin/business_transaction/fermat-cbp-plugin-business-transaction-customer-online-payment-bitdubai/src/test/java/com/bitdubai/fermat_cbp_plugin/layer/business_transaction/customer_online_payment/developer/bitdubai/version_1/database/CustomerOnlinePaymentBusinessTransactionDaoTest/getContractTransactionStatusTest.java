package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 01/02/16.
 */
public class getContractTransactionStatusTest {
    private CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    DatabaseTable databaseTable;
    private UUID testId;
    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    /*@Test
    public void getContractTransactionStatusTest_Should_Run_Once() throws Exception{
        customerOnlinePaymentBusinessTransactionDao = new CustomerOnlinePaymentBusinessTransactionDao(mockPluginDatabaseSystem,testId,mockDatabase);
        customerOnlinePaymentBusinessTransactionDao.getContractTransactionStatus(anyString());
        verify(customerOnlinePaymentBusinessTransactionDao, Mockito.times(1)).getContractTransactionStatus(anyString());
    }*/

    @Test(expected = Exception.class)
    public void getContractTransactionStatusTest_Should_Return_Exception() throws Exception{
        customerOnlinePaymentBusinessTransactionDao = new CustomerOnlinePaymentBusinessTransactionDao(null,null,null);
        customerOnlinePaymentBusinessTransactionDao.getCustomerOnlinePaymentRecord(null);
    }
}
