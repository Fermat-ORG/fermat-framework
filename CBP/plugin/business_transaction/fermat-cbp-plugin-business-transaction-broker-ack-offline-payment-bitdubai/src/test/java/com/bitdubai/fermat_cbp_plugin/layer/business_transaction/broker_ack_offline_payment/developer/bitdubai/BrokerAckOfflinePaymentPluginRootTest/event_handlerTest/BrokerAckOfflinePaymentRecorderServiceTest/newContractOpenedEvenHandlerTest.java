package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.BrokerAckOfflinePaymentPluginRootTest.event_handlerTest.BrokerAckOfflinePaymentRecorderServiceTest;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.events.EventSource;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.events.NewContractOpened;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.event_handler.BrokerAckOfflinePaymentRecorderService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * Created by juan Sulbaran sulbaranja@gmail.com on 25/02/16.
 */
public class newContractOpenedEvenHandlerTest {


    @Mock
    BrokerAckOfflinePaymentBusinessTransactionDao brokerAckOfflinePaymentBusinessTransactionDao;

    @Mock
    EventManager eventManager;

    @Mock
    FermatEventListener fermatEventListener;

    @Mock
    NewContractOpened event;

    @Mock
    EventType eventType;

    BrokerAckOfflinePaymentRecorderService brokerAckOfflinePaymentRecorderService;


    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);

        brokerAckOfflinePaymentRecorderService = new BrokerAckOfflinePaymentRecorderService(brokerAckOfflinePaymentBusinessTransactionDao, eventManager);

        setUpGeneralMockitoRules();
    }

    public void setUpGeneralMockitoRules() throws Exception {
        when(event.getEventType()).thenReturn(EventType.BROKER_ACK_PAYMENT_CONFIRMED);
        when(event.getSource()).thenReturn(EventSource.ACTOR_ASSET_ISSUER);
        when(event.getContractHash()).thenReturn("test");


    }


    @Test(expected = Exception.class)
    public void should_return_Null_pointer_exception() throws Exception {
        brokerAckOfflinePaymentRecorderService.newContractOpenedEvenHandler(null);

    }

    @Test
    public void should_return() throws Exception {
        brokerAckOfflinePaymentRecorderService.newContractOpenedEvenHandler(event);

    }
}
