package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseFactoryTest;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 29/01/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class DatabaseTest {

    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private DatabaseFactory mockDatabaseFactory;
    @Mock
    private DatabaseTableFactory mockTableFactory;
    @Mock
    private Database mockDatabase;
    @Mock
    DeveloperObjectFactory mockObjectFactory;
    private UUID testId;
    private String testDataBaseName;
    private CustomerOnlinePaymentBusinessTransactionDatabaseFactory customerOnlinePaymentBusinessTransactionDatabaseFactory;

    public void setUpTestValues() {
        testId = UUID.randomUUID();
        testDataBaseName = CustomerOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME;
    }

    public void setUpGeneralMockitoRules() throws Exception {
        when(mockPluginDatabaseSystem.createDatabase(testId, testDataBaseName)).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTableFactory);
    }

    @Before
    public void setUp() throws Exception {
        setUpTestValues();
        setUpGeneralMockitoRules();
    }

    @Test
    public void TestCreateDatabase_Should_Equal_Class() throws Exception {
        customerOnlinePaymentBusinessTransactionDatabaseFactory =
                new CustomerOnlinePaymentBusinessTransactionDatabaseFactory(mockPluginDatabaseSystem);
        Database checkDatabase = customerOnlinePaymentBusinessTransactionDatabaseFactory.createDatabase(testId, testDataBaseName);
        assertEquals(mockDatabase.getClass(), checkDatabase.getClass());
    }
}
