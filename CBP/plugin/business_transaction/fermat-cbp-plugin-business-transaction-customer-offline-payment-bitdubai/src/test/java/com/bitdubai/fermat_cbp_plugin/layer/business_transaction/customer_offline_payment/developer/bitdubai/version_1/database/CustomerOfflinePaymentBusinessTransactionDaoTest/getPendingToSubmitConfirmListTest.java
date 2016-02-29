package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
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
    private UUID testId;
    private CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;


    @Before
    public void setup()throws Exception{
        testId = UUID.randomUUID();
        MockitoAnnotations.initMocks(this);
        customerOfflinePaymentBusinessTransactionDao = new CustomerOfflinePaymentBusinessTransactionDao(mockPluginDatabaseSystem,testId, mockDatabase,errorManager);
    }
    @Test
    public void getPendingToSubmitConfirmListTest_Should_Return_Not_Null()throws Exception{
        when(mockDatabase.getTable(CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_TABLE_NAME)).thenReturn(databaseTable);
        assertNotNull(customerOfflinePaymentBusinessTransactionDao.getPendingToSubmitConfirmList());
    }
    @Test(expected = UnexpectedResultReturnedFromDatabaseException.class)
    public void getPendingToSubmitConfirmListTest_Should_Throw_Exception()throws Exception{
        customerOfflinePaymentBusinessTransactionDao = new CustomerOfflinePaymentBusinessTransactionDao(null,testId,mockDatabase,errorManager);
        customerOfflinePaymentBusinessTransactionDao.getPendingToSubmitConfirmList();
    }
}
