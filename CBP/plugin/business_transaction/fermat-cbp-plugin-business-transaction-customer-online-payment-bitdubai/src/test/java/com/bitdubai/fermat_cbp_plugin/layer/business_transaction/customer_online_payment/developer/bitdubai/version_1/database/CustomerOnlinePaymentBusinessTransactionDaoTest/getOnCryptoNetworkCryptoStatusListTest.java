package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 05/02/16.
 */
public class getOnCryptoNetworkCryptoStatusListTest {
    @Mock
    private PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    private Database mockDatabase;
    @Mock
    DatabaseTable databaseTable;
    @Mock
    ErrorManager errorManager;

    @Mock
    DatabaseTableRecord databaseTableRecord;
    List<DatabaseTableRecord> databaseTableRecordsList  = new ArrayList<>();
    private UUID testId;
    private CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;

    @Before
    public void setup()throws Exception{
        testId = UUID.randomUUID();
        MockitoAnnotations.initMocks(this);
        customerOnlinePaymentBusinessTransactionDao = new CustomerOnlinePaymentBusinessTransactionDao(
                mockPluginDatabaseSystem,testId, mockDatabase,errorManager);
        databaseTableRecordsList.add(databaseTableRecord);
        setupMockitoGeneraRules();
    }
    public void setupMockitoGeneraRules()throws Exception{
        doNothing().when(databaseTable).loadToMemory();
        when(databaseTable.getRecords()).thenReturn(databaseTableRecordsList);
        when(databaseTableRecord.getStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                        ONLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME)).thenReturn("POPC");
        when(databaseTableRecord.getStringValue(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.
                        ONLINE_PAYMENT_BLOCKCHAIN_NETWORK_TYPE_COLUMN_NAME)).thenReturn("mainnet");
    }
    @Test
    public void getOnCryptoNetworkCryptoStatusListTest_Should_Equal_ContractTransactionStatus()throws Exception{
        when(mockDatabase.getTable(CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TABLE_NAME)
        ).thenReturn(databaseTable);
        assertEquals(ContractTransactionStatus.PENDING_ONLINE_PAYMENT_CONFIRMATION,
                customerOnlinePaymentBusinessTransactionDao.getOnCryptoNetworkCryptoStatusList().
                        get(0).getContractTransactionStatus());

    }
    //Generic Exception
    @Test(expected = UnexpectedResultReturnedFromDatabaseException.class)
    public void getOnCryptoNetworkCryptoStatusListTest_Should_Throw_Exception()throws Exception{
        customerOnlinePaymentBusinessTransactionDao = new CustomerOnlinePaymentBusinessTransactionDao(
                null,testId,mockDatabase,errorManager);
        customerOnlinePaymentBusinessTransactionDao.getOnCryptoNetworkCryptoStatusList();
    }
}
