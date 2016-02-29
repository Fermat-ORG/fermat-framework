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

import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 03/02/16.
 */
public class isContractHashInDatabaseTest {
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
        when(mockDatabase.getTable(CustomerOfflinePaymentBusinessTransactionDatabaseConstants.OFFLINE_PAYMENT_TABLE_NAME)).thenReturn(databaseTable);
    }

    @Test
    public void isContractHashInDatabaseTest_Should_()throws Exception{
        customerOfflinePaymentBusinessTransactionDao.isContractHashInDatabase("65ef1c685c7a5502eef44a5f8552801d9cb4ca87");
    }
    @Test(expected = UnexpectedResultReturnedFromDatabaseException.class)
    public void isContractHashInDatabaseTest_Should_Throw_Exception()throws Exception{
        customerOfflinePaymentBusinessTransactionDao = new CustomerOfflinePaymentBusinessTransactionDao(null,null,null,errorManager);
        customerOfflinePaymentBusinessTransactionDao.isContractHashInDatabase(null);
    }
}
