package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventHandler;

import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserEventRecorderService;
import com.bitdubai.fermat_ccp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.structure.IncomingExtraUserRegistry;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by jorgegonzalez on 2015.07.03..
 */
public class HandleEventTest {

    @Mock
    private EventManager mockEventManager;
    @Mock
    private IncomingExtraUserRegistry mockRegistry;

    private IncomingExtraUserEventRecorderService testRecorderService;
    private IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventHandler testEventHandler;

    @Before
    public void setUpRecorderService(){
        testRecorderService = new IncomingExtraUserEventRecorderService(mockEventManager, mockRegistry);
    }

    @Test
    public void HandleEvent_TestRecorderNotStarted_ThrowsException() throws Exception{
        testEventHandler = new IncomingCryptoReversedOnCryptoNetworkWaitingTransferenceExtraUserEventHandler(testRecorderService);
        catchException(testEventHandler).handleEvent(null);
        assertThat(caughtException()).isInstanceOf(TransactionServiceNotStartedException.class);
    }
}
