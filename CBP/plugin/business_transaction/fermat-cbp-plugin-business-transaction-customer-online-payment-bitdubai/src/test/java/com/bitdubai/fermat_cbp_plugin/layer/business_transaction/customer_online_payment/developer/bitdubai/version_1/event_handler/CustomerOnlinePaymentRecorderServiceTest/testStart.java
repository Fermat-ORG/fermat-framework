package com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.event_handler.CustomerOnlinePaymentRecorderServiceTest;

import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.database.CustomerOnlinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.customer_online_payment.developer.bitdubai.version_1.event_handler.CustomerOnlinePaymentRecorderService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

/**
 * Created by alexander jimenez (alex_jimenez76@hotmail.com) on 02/02/16.
 */
public class testStart {
    @Mock
    CustomerOnlinePaymentBusinessTransactionDao customerOnlinePaymentBusinessTransactionDao;
    @Mock
    EventManager eventManager;
    @Mock
    ErrorManager errorManager;
    @Mock
    FermatEventListener mockFermatEventListener;

    CustomerOnlinePaymentRecorderService customerOnlinePaymentRecorderService;

    public void setUpGeneralMockitoRules() throws Exception {
        when(eventManager.getNewListener(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE)).thenReturn(mockFermatEventListener);
        when(eventManager.getNewListener(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE)).thenReturn(mockFermatEventListener);

    }

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        setUpGeneralMockitoRules();
    }

    @Test
    public void testStart_Should_Return_Start() throws Exception {
        customerOnlinePaymentRecorderService = new CustomerOnlinePaymentRecorderService(
                customerOnlinePaymentBusinessTransactionDao, eventManager, errorManager);
        customerOnlinePaymentRecorderService.setEventManager(eventManager);
        customerOnlinePaymentRecorderService.start();
        assertEquals(ServiceStatus.STARTED, customerOnlinePaymentRecorderService.getStatus());
    }

    @Test(expected = CantStartServiceException.class)
    public void testStart_Should_Return_Exception() throws Exception {
        customerOnlinePaymentRecorderService = new CustomerOnlinePaymentRecorderService(null, null, errorManager);
        customerOnlinePaymentRecorderService.start();
    }

    @Test
    public void testStop_Should_Return_Stop() throws Exception {
        customerOnlinePaymentRecorderService = new CustomerOnlinePaymentRecorderService(
                customerOnlinePaymentBusinessTransactionDao, eventManager, errorManager);
        customerOnlinePaymentRecorderService.setEventManager(eventManager);
        customerOnlinePaymentRecorderService.start();
        customerOnlinePaymentRecorderService.stop();
        assertEquals(customerOnlinePaymentRecorderService.getStatus(), ServiceStatus.STOPPED);
    }
}
