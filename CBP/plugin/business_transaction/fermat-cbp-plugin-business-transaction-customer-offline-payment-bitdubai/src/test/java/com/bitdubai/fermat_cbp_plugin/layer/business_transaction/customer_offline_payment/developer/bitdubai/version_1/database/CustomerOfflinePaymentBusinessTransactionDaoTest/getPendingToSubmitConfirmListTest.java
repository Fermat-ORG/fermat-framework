package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDatabaseConstants;
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
public class getPendingToSubmitConfirmListTest {
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
    private CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;


    @Before
    public void setup()throws Exception{
        testId = UUID.randomUUID();
        MockitoAnnotations.initMocks(this);
        customerOfflinePaymentBusinessTransactionDao = new CustomerOfflinePaymentBusinessTransactionDao(
                mockPluginDatabaseSystem,testId, mockDatabase,errorManager);
        databaseTableRecordsList.add(databaseTableRecord);
        setupMockitoGeneraRules();
    }
    public void setupMockitoGeneraRules()throws Exception{
        doNothing().when(databaseTable).loadToMemory();
        when(databaseTable.getRecords()).thenReturn(databaseTableRecordsList);
        when(databaseTableRecord.getStringValue(
                CustomerOfflinePaymentBusinessTransactionDatabaseConstants.
                        OFFLINE_PAYMENT_CONTRACT_TRANSACTION_STATUS_COLUMN_NAME)).thenReturn("POFC");
    }
    @Test
    public void getPendingToSubmitConfirmListTest()throws Exception{
        when(mockDatabase.getTable(CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_TABLE_NAME)).
                thenReturn(databaseTable);
        assertEquals(ContractTransactionStatus.PENDING_OFFLINE_PAYMENT_CONFIRMATION,
                customerOfflinePaymentBusinessTransactionDao.getPendingToSubmitConfirmList().get(0).getContractTransactionStatus());
    }
    @Test(expected = UnexpectedResultReturnedFromDatabaseException.class)
    public void getPendingToSubmitConfirmListTest_Should_Throw_Exception()throws Exception{
        customerOfflinePaymentBusinessTransactionDao = new CustomerOfflinePaymentBusinessTransactionDao(
                null,testId,mockDatabase,errorManager);
        customerOfflinePaymentBusinessTransactionDao.getPendingToSubmitConfirmList();
    }
}
