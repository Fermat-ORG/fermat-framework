package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactoryTest;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 03/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class initializeDatabaseTest {

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    private Database mockDatabase;
    private UUID testId;
    private CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory
            customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory;

    public void setUpTestValues() {
        testId = UUID.randomUUID();
    }

    @Before
    public void setUp() throws Exception {
        setUpTestValues();
    }

    @Test
    public void TestInitializeDatabaseTest_Should_() throws Exception {
        customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory =
                new CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory(mockPluginDatabaseSystem, testId);
        customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory.initializeDatabase();
        Mockito.verify(mockPluginDatabaseSystem, Mockito.times(1)).openDatabase(
                testId, CustomerOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME);
    }
}
