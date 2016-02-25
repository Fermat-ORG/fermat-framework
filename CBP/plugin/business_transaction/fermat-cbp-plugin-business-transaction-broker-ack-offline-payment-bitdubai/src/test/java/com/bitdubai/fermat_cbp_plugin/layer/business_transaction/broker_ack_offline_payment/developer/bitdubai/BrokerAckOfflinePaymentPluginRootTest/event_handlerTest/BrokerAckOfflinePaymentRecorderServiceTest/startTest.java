package event_handlerTest.BrokerAckOfflinePaymentRecorderServiceTest;

import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_cbp_api.all_definition.events.enums.EventType;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantSetObjectException;
import com.bitdubai.fermat_cbp_api.all_definition.exceptions.CantStartServiceException;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.database.BrokerAckOfflinePaymentBusinessTransactionDao;
import com.bitdubai.fermat_cbp_plugin.layer.business_transaction.broker_ack_offline_payment.developer.bitdubai.version_1.event_handler.BrokerAckOfflinePaymentRecorderService;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
    public void setup()throws Exception{
        MockitoAnnotations.initMocks(this);
        brokerAckOfflinePaymentRecorderService = new BrokerAckOfflinePaymentRecorderService(brokerAckOfflinePaymentBusinessTransactionDao, eventManager);

        setUpGeneralMockitoRules();


    }

    public void setUpGeneralMockitoRules()throws Exception{
        when(eventManager.getNewListener(EventType.INCOMING_NEW_CONTRACT_STATUS_UPDATE)).thenReturn(fermatEventListener);
        when(eventManager.getNewListener(EventType.INCOMING_CONFIRM_BUSINESS_TRANSACTION_RESPONSE)).thenReturn(fermatEventListener);
        when(eventManager.getNewListener(EventType.NEW_CONTRACT_OPENED)).thenReturn(fermatEventListener);


    }


    @Test
    public void Should_throw_CantStartServiceException() throws Exception {
        brokerAckOfflinePaymentRecorderService = new BrokerAckOfflinePaymentRecorderService(brokerAckOfflinePaymentBusinessTransactionDao, eventManager);

        brokerAckOfflinePaymentRecorderService.start();



    }


    @Test
    public void Should_throw_() throws Exception {

        brokerAckOfflinePaymentRecorderService.start();
    }


}
