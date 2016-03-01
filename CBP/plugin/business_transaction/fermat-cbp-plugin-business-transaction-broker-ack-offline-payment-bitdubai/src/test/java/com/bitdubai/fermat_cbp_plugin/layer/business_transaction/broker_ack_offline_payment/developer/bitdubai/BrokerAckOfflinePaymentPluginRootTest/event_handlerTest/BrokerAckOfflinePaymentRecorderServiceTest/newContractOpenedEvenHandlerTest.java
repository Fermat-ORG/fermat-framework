package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.BrokerAckOfflinePaymentPluginRootTest.event_handlerTest.BrokerAckOfflinePaymentRecorderServiceTest;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.layer.business_transaction.open_contract.events.NewContractOpened;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.event_handler.BrokerAckOfflinePaymentRecorderService;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

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

    BrokerAckOfflinePaymentRecorderService brokerAckOfflinePaymentRecorderService;


    @Before
    public void setup()throws Exception{
        MockitoAnnotations.initMocks(this);

        //brokerAckOfflinePaymentRecorderService = new BrokerAckOfflinePaymentRecorderService(brokerAckOfflinePaymentBusinessTransactionDao, eventManager);

        setUpGeneralMockitoRules();
    }
    public void setUpGeneralMockitoRules()throws Exception{
        when(event.getEventType().getCode()).thenReturn(anyString());
        when(event.getSource().getCode()).thenReturn(anyString());
        when(event.getContractHash()).thenReturn(anyString());
    }



    @Test
    public void should_return_() throws Exception{
        brokerAckOfflinePaymentRecorderService.newContractOpenedEvenHandler(event);

    }
}
