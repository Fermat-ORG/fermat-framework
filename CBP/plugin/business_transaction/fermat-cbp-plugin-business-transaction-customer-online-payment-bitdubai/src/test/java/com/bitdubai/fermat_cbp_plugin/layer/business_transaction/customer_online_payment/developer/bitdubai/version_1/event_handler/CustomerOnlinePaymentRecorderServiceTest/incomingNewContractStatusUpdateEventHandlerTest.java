package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.event_handler.CustomerOnlinePaymentRecorderServiceTest;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSaveEventException;
import com.bitdubai.fermat_cbp_api.layer.network_service.transaction_transmission.events.IncomingNewContractStatusUpdate;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.event_handler.CustomerOnlinePaymentRecorderService;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 18/02/16.
 */
public class incomingNewContractStatusUpdateEventHandlerTest {
    @Mock
    CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;
    @Mock
    EventManager eventManager;
    @Mock
    ErrorManager errorManager;
    @Mock
    FermatEventListener mockFermatEventListener;
    IncomingNewContractStatusUpdate incomingNewContractStatusUpdate;
    CustomerOnlinePaymentRecorderService customerOnlinePaymentRecorderService;

    public void setUpGeneralMockitoRules() throws Exception{
        when(eventManager.getNewListener(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE)).thenReturn(mockFermatEventListener);
        when(eventManager.getNewListener(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE)).thenReturn(mockFermatEventListener);


    }
    @Before
    public void setup()throws Exception{
        MockitoAnnotations.initMocks(this);
        setUpGeneralMockitoRules();
    }
    /*@Test
    public void incomingNewContractStatusUpdateEventHandlerTest_Should_Return_() throws Exception {
        when(incomingNewContractStatusUpdate.getRemoteBusinessTransaction()).thenReturn(Plugins.CUSTOMER_ONLINE_PAYMENT);
        customerOnlinePaymentRecorderService = new CustomerOnlinePaymentRecorderService(customerOnlinePaymentBusinessTransactionDao,eventManager,errorManager);
        //customerOnlinePaymentRecorderService.incomingNewContractStatusUpdateEventHandler(incomingNewContractStatusUpdate);
    }*/

    @Test(expected = Exception.class)
    public void incomingNewContractStatusUpdateEventHandlerTest_Should_Throw_Exception() throws Exception {
        customerOnlinePaymentRecorderService = new CustomerOnlinePaymentRecorderService(customerOnlinePaymentBusinessTransactionDao,eventManager,errorManager);
        customerOnlinePaymentRecorderService.setEventManager(eventManager);
        customerOnlinePaymentRecorderService.start();
        customerOnlinePaymentRecorderService.incomingNewContractStatusUpdateEventHandler(null);
    }

    @Test(expected = CantSaveEventException.class)
    public void incomingNewContractStatusUpdateEventHandlerTest_Should_Throw_CantSaveEventException() throws Exception {
        customerOnlinePaymentRecorderService = new CustomerOnlinePaymentRecorderService(customerOnlinePaymentBusinessTransactionDao,eventManager,errorManager);
        customerOnlinePaymentRecorderService.setEventManager(eventManager);
        customerOnlinePaymentRecorderService.start();
        customerOnlinePaymentRecorderService.incomingNewContractStatusUpdateEventHandler(incomingNewContractStatusUpdate);
    }
}
