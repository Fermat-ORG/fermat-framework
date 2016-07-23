package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.BrokerAckOfflinePaymentPluginRootTest.event_handlerTest.BrokerAckOfflinePaymentRecorderServiceTest;

/**
 * Created by Juan Sulbaran sulbaranja@gmail.com on 26/02/16.
 */


import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.Plugins;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingNewContractStatusUpdate;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.event_handler.BrokerAckOfflinePaymentRecorderService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.doNothing;


public class incomingNewContractStatusUpdateEventHandlerTest {


    BrokerAckOfflinePaymentRecorderService brokerAckOfflinePaymentRecorderService;

    @Mock
    EventManager eventManager;


    @Mock
    BrokerAckOfflinePaymentBusinessTransactionDao brokerAckOfflinePaymentBusinessTransactionDao;

    IncomingNewContractStatusUpdate event;

    EventSource eventSource = EventSource.ACTOR_ASSET_ISSUER;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        brokerAckOfflinePaymentRecorderService = new BrokerAckOfflinePaymentRecorderService(brokerAckOfflinePaymentBusinessTransactionDao, eventManager);
        event = new IncomingNewContractStatusUpdate(EventType.BROKER_ACK_PAYMENT_CONFIRMED);
        setUpGeneralMockitoRules();
    }


    public void setUpGeneralMockitoRules() throws Exception {

        doNothing().when(brokerAckOfflinePaymentBusinessTransactionDao).saveNewEvent(
                EventType.BROKER_ACK_PAYMENT_CONFIRMED.getCode(),
                eventSource.getCode());


    }


    @Test
    public void should_return_() throws Exception {

        event.setRemoteBusinessTransaction(Plugins.BROKER_ACK_OFFLINE_PAYMENT);
        event.setSource(eventSource);


        brokerAckOfflinePaymentRecorderService.incomingNewContractStatusUpdateEventHandler(event);
    }
}
