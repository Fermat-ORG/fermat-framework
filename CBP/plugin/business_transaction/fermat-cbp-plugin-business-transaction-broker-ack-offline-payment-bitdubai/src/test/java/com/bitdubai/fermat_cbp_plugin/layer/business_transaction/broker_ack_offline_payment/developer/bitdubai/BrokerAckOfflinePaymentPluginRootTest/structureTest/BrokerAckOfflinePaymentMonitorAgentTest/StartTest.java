package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.BrokerAckOfflinePaymentPluginRootTest.structureTest.BrokerAckOfflinePaymentMonitorAgentTest;


import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_bnk_api.layer.bnk_bank_money_transaction.deposit.interfaces.DepositManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_purchase.interfaces.CustomerBrokerContractPurchaseManager;
import com.bitdubai.fermat_cbp_api.layer.contract.customer_broker_sale.interfaces.CustomerBrokerContractSaleManager;
import com.bitdubai.fermat_cbp_api.layer.negotiation.customer_broker_sale.interfaces.CustomerBrokerSaleNegotiationManager;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.interfaces.TransactionTransmissionManager;
import com.bitdubai.fermat_cbp_api.layer.wallet.crypto_broker.interfaces.CryptoBrokerWalletManager;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.structure.BrokerAckOfflinePaymentMonitorAgent;
import com.bitdubai.fermat_ccp_api.layer.crypto_transaction.outgoing_intra_actor.interfaces.OutgoingIntraActorManager;
import com.bitdubai.fermat_csh_api.layer.csh_cash_money_transaction.deposit.interfaces.CashDepositTransactionManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 29/02/16.
 */
public class StartTest {

    BrokerAckOfflinePaymentMonitorAgent brokerAckOfflinePaymentMonitorAgent;

    @Mock
    OutgoingIntraActorManager outgoingIntraActorManager;
    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;
    @Mock
    LogManager logManager;
    @Mock
    ErrorManager errorManager;
    @Mock
    EventManager eventManager;
    @Mock
    TransactionTransmissionManager transactionTransmissionManager;
    @Mock
    CustomerBrokerContractPurchaseManager customerBrokerContractPurchaseManager;
    @Mock
    CustomerBrokerContractSaleManager customerBrokerContractSaleManager;
    @Mock
    CustomerBrokerSaleNegotiationManager customerBrokerSaleNegotiationManager;
    @Mock
    DepositManager depositManager;
    @Mock
    CryptoBrokerWalletManager cryptoBrokerWalletManager;
    @Mock
    CashDepositTransactionManager cashDepositTransactionManager;


    private UUID pluginId;


    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        pluginId = UUID.randomUUID();
        brokerAckOfflinePaymentMonitorAgent = new BrokerAckOfflinePaymentMonitorAgent(
                pluginDatabaseSystem,
                logManager,
                errorManager,
                eventManager,
                pluginId,
                transactionTransmissionManager,
                customerBrokerContractPurchaseManager,
                customerBrokerContractSaleManager,
                customerBrokerSaleNegotiationManager,
                depositManager,
                cryptoBrokerWalletManager,
                cashDepositTransactionManager);


    }


    @Test
    public void test_start() throws Exception {


        brokerAckOfflinePaymentMonitorAgent.setEventManager(eventManager);
        brokerAckOfflinePaymentMonitorAgent.setErrorManager(errorManager);
        brokerAckOfflinePaymentMonitorAgent.setPluginDatabaseSystem(pluginDatabaseSystem);
        brokerAckOfflinePaymentMonitorAgent.setLogManager(logManager);
        brokerAckOfflinePaymentMonitorAgent.setPluginId(pluginId);
        brokerAckOfflinePaymentMonitorAgent.start();
    }


    @Test
    public void testStop() throws Exception {
        brokerAckOfflinePaymentMonitorAgent.start();
        brokerAckOfflinePaymentMonitorAgent.stop();

    }

/*
    @Test(expected = CantSetObjectException.class)
    public void testSetIntraActorCryptoTransactionManager() throws Exception {
        brokerAckOfflinePaymentMonitorAgent = new BrokerAckOfflinePaymentMonitorAgent(null,null,errorManager,null,null,null,null,null,null,null,null,null);
    }
*/
}
