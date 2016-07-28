package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactoryTest;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperDatabaseTable;
import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 03/02/16.
 */
@RunWith(MockitoJUnitRunner.class)
public class getDatabaseTableListTest {
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    DeveloperObjectFactory developerObjectFactory;
    @Mock
    private Database mockDatabase;
    @Mock
    DeveloperDatabaseTable developerDatabaseTable;
    ArrayList arrayList = new ArrayList();
    private UUID testId;
    private String testDataBaseName;
    private CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory
            customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory;

    public void setUpTestValues() {
        arrayList.add(developerDatabaseTable);
        arrayList.add(developerDatabaseTable);
        testId = UUID.randomUUID();
        testDataBaseName = CustomerOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME;
    }

    public void setUpGeneralMockitoRules() throws Exception {
        when(mockPluginDatabaseSystem.createDatabase(testId, testDataBaseName)).thenReturn(mockDatabase);
        when(developerObjectFactory.getNewDeveloperDatabaseTable(anyString(), anyList())).thenReturn(developerDatabaseTable);
    }

    @Before
    public void setUp() throws Exception {
        setUpTestValues();
        setUpGeneralMockitoRules();
    }

    @Test
    public void getDatabaseTableListTest_Should_Return_Not_Null() throws Exception {
        customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory = new
                CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory(mockPluginDatabaseSystem, testId);
        assertEquals(arrayList, customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory.
                getDatabaseTableList(developerObjectFactory));
    }
}
