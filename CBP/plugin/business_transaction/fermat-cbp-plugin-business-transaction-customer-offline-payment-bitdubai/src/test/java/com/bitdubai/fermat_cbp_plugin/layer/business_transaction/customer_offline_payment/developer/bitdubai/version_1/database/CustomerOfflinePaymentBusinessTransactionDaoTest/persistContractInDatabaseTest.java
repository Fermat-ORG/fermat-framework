package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_api.layer.all_definition.enums.BlockchainNetworkType;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantInsertRecordException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchase;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSale;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 25/02/16.
 */
public class persistContractInDatabaseTest {
    CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    Database mockDatabase;
    @Mock
    ErrorManager errorManager;
    @Mock
    CustomerBrokerContractPurchase customerBrokerContractPurchase;
    @Mock
    DatabaseTable databaseTable;
    @Mock
    DatabaseTableRecord databaseTableRecord;
    @Mock
    CustomerBrokerContractSale customerBrokerContractSale;
    private UUID testId;
    @Before
    public void setup(){
        testId = UUID.randomUUID();
        MockitoAnnotations.initMocks(this);
        customerOfflinePaymentBusinessTransactionDao = new CustomerOfflinePaymentBusinessTransactionDao(pluginDatabaseSystem,testId,mockDatabase,errorManager);
        when(databaseTable.getEmptyRecord()).thenReturn(databaseTableRecord);
    }
    //Test First persistContractInDatabaseTest method in Dao
    @Test
    public void persistContractInDatabaseTest() throws Exception{
        when(mockDatabase.getTable(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_TABLE_NAME
        )).thenReturn(databaseTable);
        customerOfflinePaymentBusinessTransactionDao.persistContractInDatabase(customerBrokerContractPurchase);
    }
    @Test(expected = CantInsertRecordException.class)
    public void persistContractInDatabaseTest_Should_Throw_Exception() throws Exception{
        customerOfflinePaymentBusinessTransactionDao.persistContractInDatabase(customerBrokerContractPurchase);
    }
    //Test Second persistContractInDatabaseTest method in Dao
    @Test
    public void persistContractInDatabaseTest_() throws Exception{
        when(mockDatabase.getTable(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_TABLE_NAME
        )).thenReturn(databaseTable);
        customerOfflinePaymentBusinessTransactionDao.persistContractInDatabase(customerBrokerContractSale);
    }
    @Test(expected = CantInsertRecordException.class)
    public void persistContractInDatabaseTest_Should_Throw_Exception_() throws Exception{
        customerOfflinePaymentBusinessTransactionDao.persistContractInDatabase(customerBrokerContractSale);
    }
}
