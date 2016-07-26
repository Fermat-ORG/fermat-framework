package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.interfaces.BusinessTransactionRecord;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 25/02/16.
 */
public class updateBusinessTransactionRecordTest {
    CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    Database mockDatabase;
    @Mock
    ErrorManager errorManager;
    @Mock
    DatabaseTable databaseTable;
    @Mock
    List<DatabaseTableRecord> databaseTableRecordList;
    @Mock
    DatabaseTableRecord databaseTableRecord;
    @Mock
    BusinessTransactionRecord businessTransactionRecord;
    @Mock
    CryptoAddress cryptoAddress;
    private UUID testId;

    @Before
    public void setup() throws Exception {
        testId = UUID.randomUUID();
        MockitoAnnotations.initMocks(this);
        customerOnlinePaymentBusinessTransactionDao = new CustomerOnlinePaymentBusinessTransactionDao(
                pluginDatabaseSystem, testId, mockDatabase, errorManager);
        when(databaseTable.getRecords()).thenReturn(databaseTableRecordList);
        when(databaseTableRecordList.get(0)).thenReturn(databaseTableRecord);
        when(businessTransactionRecord.getBrokerPublicKey()).thenReturn("publicKey");
        when(businessTransactionRecord.getContractHash()).thenReturn("ContractHash");
        when(businessTransactionRecord.getContractTransactionStatus()).thenReturn(
                ContractTransactionStatus.PENDING_ONLINE_PAYMENT_CONFIRMATION);
        when(businessTransactionRecord.getCryptoAddress()).thenReturn(cryptoAddress);
        when(businessTransactionRecord.getCryptoStatus()).thenReturn(CryptoStatus.PENDING_SUBMIT);
        doNothing().when(databaseTable).updateRecord(databaseTableRecord);
    }

    @Test
    public void updateBusinessTransactionRecordTest() throws Exception {
        when(mockDatabase.getTable(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TABLE_NAME
        )).thenReturn(databaseTable);
        customerOnlinePaymentBusinessTransactionDao.updateBusinessTransactionRecord(businessTransactionRecord);
        verify(databaseTable, times(1)).updateRecord(databaseTableRecord);
    }

    @Test(expected = UnexpectedResultReturnedFromDatabaseException.class)
    public void updateBusinessTransactionRecordTest_Should_Throw_Exception() throws Exception {
        customerOnlinePaymentBusinessTransactionDao.updateBusinessTransactionRecord(businessTransactionRecord);
    }
}
