package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactoryTest;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabase;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 03/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class getDatabaseListTest {
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    DeveloperObjectFactory developerObjectFactory;
    @Mock
    DeveloperDatabase developerDatabase;
    private UUID testId;
    private CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory
            customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory;

    public void setUpTestValues() {
        testId = UUID.randomUUID();
    }

    public void setUpGeneralMockitoRules() throws Exception {
        when(developerObjectFactory.getNewDeveloperDatabase("Customer Online Payment", testId.toString())).thenReturn(developerDatabase);
    }

    @Before
    public void setUp() throws Exception {
        setUpTestValues();
        setUpGeneralMockitoRules();
    }

    @Test
    public void getDatabaseListTest_Should_Return_ArrayList() throws Exception {
        customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory =
                new CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory(mockPluginDatabaseSystem, testId);
        assertEquals(java.util.ArrayList.class, customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory.
                getDatabaseList(developerObjectFactory).getClass());
    }
}
