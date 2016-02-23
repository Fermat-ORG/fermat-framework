package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;


import org.powermock.api.mockito.PowerMockito;

import static org.junit.Assert.assertNotNull;



/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 03/02/16.
 */
public class getPendingCryptoTransactionListTest {

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    DatabaseTable databaseTable;
    private UUID testId;
    private CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;
    private CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDaoSpy;


    @Before
    public void setup()throws Exception{
        testId = UUID.randomUUID();
        customerOfflinePaymentBusinessTransactionDao = new CustomerOfflinePaymentBusinessTransactionDao(mockPluginDatabaseSystem,testId, mockDatabase);
        customerOfflinePaymentBusinessTransactionDaoSpy = PowerMockito.spy( customerOfflinePaymentBusinessTransactionDao);
        MockitoAnnotations.initMocks(this);
        PowerMockito.doReturn(databaseTable).when(customerOfflinePaymentBusinessTransactionDaoSpy, "getDatabaseContractTable");
    }

    @Test
    public void getPendingCryptoTransactionListTest_Should_()throws Exception{
        customerOfflinePaymentBusinessTransactionDaoSpy.getPendingCryptoTransactionList();
    }
}
