package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.BrokerAckOfflinePaymentPluginRootTest.event_handlerTest.BrokerAckOfflinePaymentRecorderServiceTest;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.event_handler.BrokerAckOfflinePaymentRecorderService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by Juan Sulbaran (sulbaranja@gmail.com) on 23/02/16.
 */
public class startTest {
    @Mock
    BrokerAckOfflinePaymentBusinessTransactionDao brokerAckOfflinePaymentBusinessTransactionDao;
    @Mock
    EventManager eventManager;
    @Mock
    FermatEventListener fermatEventListener;
    BrokerAckOfflinePaymentRecorderService brokerAckOfflinePaymentRecorderService;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        brokerAckOfflinePaymentRecorderService = new BrokerAckOfflinePaymentRecorderService(brokerAckOfflinePaymentBusinessTransactionDao, eventManager);

        setUpGeneralMockitoRules();


    }

    public void setUpGeneralMockitoRules() throws Exception {
        when(eventManager.getNewListener(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE)).thenReturn(fermatEventListener);
        when(eventManager.getNewListener(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE)).thenReturn(fermatEventListener);
        when(eventManager.getNewListener(EventType.NEW_CONTRACT_OPENED)).thenReturn(fermatEventListener);


    }


    @Test
    public void testStart_Should_Return_Start() throws Exception {
        brokerAckOfflinePaymentRecorderService = new BrokerAckOfflinePaymentRecorderService(brokerAckOfflinePaymentBusinessTransactionDao, eventManager);
        brokerAckOfflinePaymentRecorderService.start();
        assertEquals(ServiceStatus.STARTED, brokerAckOfflinePaymentRecorderService.getStatus());


    }

    @Test(expected = CantStartServiceException.class)
    public void Should_throw_CantStartServiceException() throws Exception {
        brokerAckOfflinePaymentRecorderService = new BrokerAckOfflinePaymentRecorderService(null, eventManager);
        brokerAckOfflinePaymentRecorderService.start();

        //brokerAckOfflinePaymentRecorderService.start();
    }


    @Test
    public void testStop_Should_Return_Stop() throws Exception {
        brokerAckOfflinePaymentRecorderService = new BrokerAckOfflinePaymentRecorderService(brokerAckOfflinePaymentBusinessTransactionDao, eventManager);
        brokerAckOfflinePaymentRecorderService.setEventManager(eventManager);
        brokerAckOfflinePaymentRecorderService.start();
        brokerAckOfflinePaymentRecorderService.stop();
        assertEquals(brokerAckOfflinePaymentRecorderService.getStatus(), ServiceStatus.STOPPED);
    }

}
