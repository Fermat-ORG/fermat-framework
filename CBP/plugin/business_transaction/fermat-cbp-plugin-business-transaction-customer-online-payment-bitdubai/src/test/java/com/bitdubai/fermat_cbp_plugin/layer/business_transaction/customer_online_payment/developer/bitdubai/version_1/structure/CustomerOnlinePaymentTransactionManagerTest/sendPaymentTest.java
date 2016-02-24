package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.structure.CustomerOnlinePaymentTransactionManagerTest;

import com.bitdubai.fermat_cbp_api.layer.business_transaction.common.exceptions.CantSendPaymentException;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_purchase.interfaces.CustomerBrokerPurchaseNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.structure.CustomerOnlinePaymentTransactionManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 18/02/16.
 */
public class sendPaymentTest {

    CustomerOnlinePaymentTransactionManager customerOnlinePaymentTransactionManager;
    @Mock
    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    @Mock
    CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;
    @Mock
    ErrorManager errorManager;
    @Mock
    TransactionTransmissionManager transactionTransmissionManager;
    @Mock
    CustomerBrokerPurchaseNegotiationManager customerBrokerPurchaseNegotiationManager;
    @Before
    public void setup() throws Exception{
        MockitoAnnotations.initMocks(this);
    }

    /*@Test
    public void sendPaymentTest_Should() throws Exception{
        customerOnlinePaymentTransactionManager = new CustomerOnlinePaymentTransactionManager(customerBrokerContractPurchaseManager,
                customerOnlinePaymentBusinessTransactionDao,
                transactionTransmissionManager,
                customerBrokerPurchaseNegotiationManager);
        customerOnlinePaymentTransactionManager.sendPayment("","");
    }*/
/*
    @Test(expected = CantSendPaymentException.class)
    public void sendPaymentTest_Should_Throw_CantSendPaymentException() throws Exception{
        customerOnlinePaymentTransactionManager = new CustomerOnlinePaymentTransactionManager(null,
                customerOnlinePaymentBusinessTransactionDao,
                transactionTransmissionManager,
                customerBrokerPurchaseNegotiationManager);
        customerOnlinePaymentTransactionManager.sendPayment("","");
    }*/

    @Test(expected = Exception.class)
    public void sendPaymentTest_Should_Throw_Exception() throws Exception{
        customerOnlinePaymentTransactionManager = new CustomerOnlinePaymentTransactionManager(null,null,null,null);
        customerOnlinePaymentTransactionManager.sendPayment(null,null);
    }
}
