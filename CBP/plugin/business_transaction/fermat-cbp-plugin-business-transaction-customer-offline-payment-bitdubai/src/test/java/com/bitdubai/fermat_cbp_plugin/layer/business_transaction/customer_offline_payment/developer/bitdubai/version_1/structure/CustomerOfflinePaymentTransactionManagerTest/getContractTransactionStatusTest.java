package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.structure.CustomerOfflinePaymentTransactionManagerTest;

import com.bitdubai.fermat_cbp_api.all_definition.enums.ContractTransactionStatus;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.database.CustomerOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_offline_payment.developer.bitdubai.version_1.structure.CustomerOfflinePaymentTransactionManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 16/02/16.
 */
public class getContractTransactionStatusTest {
    CustomerOfflinePaymentTransactionManager customerOfflinePaymentTransactionManager;
    @Mock
    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    @Mock
    CustomerOfflinePaymentBusinessTransactionDao customerOfflinePaymentBusinessTransactionDao;
    @Mock
    ErrorManager errorManager;

    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
        when(customerOfflinePaymentBusinessTransactionDao.getContractTransactionStatus(anyString())).thenReturn(ContractTransactionStatus.ACK_OFFLINE_PAYMENT);
    }

    @Test
    public void getContractTransactionStatusTest_Should_Return_Not_Null() throws Exception{
        customerOfflinePaymentTransactionManager = new CustomerOfflinePaymentTransactionManager(customerBrokerContractPurchaseManager,
                customerOfflinePaymentBusinessTransactionDao,
                errorManager);
        assertNotNull(customerOfflinePaymentTransactionManager.getContractTransactionStatus(""));
    }

    @Test(expected = Exception.class)
    public void getContractTransactionStatusTest_Should_Throw_Exception() throws Exception{
        customerOfflinePaymentTransactionManager = new CustomerOfflinePaymentTransactionManager(null,null,null);
        customerOfflinePaymentTransactionManager.getContractTransactionStatus(null);
    }
}
