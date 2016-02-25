package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDaoTest;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.UnexpectedResultReturnedFromDatabaseException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDatabaseConstants;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 25/02/16.
 */
public class getPendingToSubmitCryptoStatusListTest {
    CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    Database database;
    @Mock
    ErrorManager errorManager;
    @Mock
    DatabaseTable databaseTable;
    private UUID testId;

    @Before
    public void setup(){
        testId = UUID.randomUUID();
        MockitoAnnotations.initMocks(this);
        customerOnlinePaymentBusinessTransactionDao = new CustomerOnlinePaymentBusinessTransactionDao(
                pluginDatabaseSystem,testId,database,errorManager);
    }
    @Test
    public void getPendingToSubmitCryptoStatusListTest_Should_Return_Not_Null()throws Exception{
        when(database.getTable(
                CustomerOnlinePaymentBusinessTransactionDatabaseConstants.ONLINE_PAYMENT_TABLE_NAME
        )).thenReturn(databaseTable);
        assertNotNull(customerOnlinePaymentBusinessTransactionDao.getPendingToSubmitCryptoStatusList());
    }
    @Test(expected = UnexpectedResultReturnedFromDatabaseException.class)
    public void getPendingToSubmitCryptoStatusListTest_Should_Throw_Exception()throws Exception{
        customerOnlinePaymentBusinessTransactionDao.getPendingToSubmitCryptoStatusList();
    }
}
