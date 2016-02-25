package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactoryTest;

import com.bitdubai.fermat_api.layer.all_definition.developer.DeveloperObjectFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.exceptions.CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

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
    private String testDataBaseName;
    private CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory;
    public void setUpTestValues(){
        testId = UUID.randomUUID();
        testDataBaseName = CustomerOnlinePaymentBusinessTransactionDatabaseConstants.DATABASE_NAME;
    }

    public void setUpGeneralMockitoRules() throws Exception{
        when(mockPluginDatabaseSystem.createDatabase(testId, testDataBaseName)).thenReturn(mockDatabase);

    }

    @Before
    public void setUp() throws Exception{
        setUpTestValues();
        setUpGeneralMockitoRules();
    }
/*
    @Test
    public void TestInitializeDatabaseTest_Should_() throws Exception{
        customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory = new CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory(mockPluginDatabaseSystem,testId);
        customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory.initializeDatabase();
        //FALTA CONFIRMACION!!!!!!!!!!!!!!

    }*/
    @Test(expected = Exception.class)
    public void TestCreateDatabase_Should_Return_Exception() throws Exception{
        customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory = new CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory(null,null);
        customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory.initializeDatabase();
    }/*
    @Test(expected = CantInitializeCustomerOnlinePaymentBusinessTransactionDatabaseException.class )
    public void TestCreateDatabase_Should_Return_() throws Exception{
        customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory = new CustomerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory(null,testId);
        customerOnlinePaymentBusinessTransactionDeveloperDatabaseFactory.initializeDatabase();
    }*/
}
