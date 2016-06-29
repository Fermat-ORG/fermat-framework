package unit.com.bitdubai.fermat_dmp_plugin.layer.transaction.incoming_extra_user.developer.bitdubai.version_1.event_handlers.IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEventHandler;

import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.event_handlers.IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEventHandler;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserEventRecorderService;
import com.bitdubai.fermat_ccp_plugin.layer.crypto_transaction.incoming_extra_actor.developer.bitdubai.version_1.structure.IncomingExtraUserRegistry;

import static org.fest.assertions.api.Assertions.*;
import static com.googlecode.catchexception.CatchException.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

/**
 * Created by jorgegonzalez on 2015.07.03..
 */
public class HandleEventTest {

    @Mock
    private EventManager mockEventManager;
    @Mock
    private IncomingExtraUserRegistry mockRegistry;

    private IncomingExtraUserEventRecorderService testRecorderService;
    private IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEventHandler testEventHandler;

    @Before
    public void setUpRecorderService(){
        testRecorderService = new IncomingExtraUserEventRecorderService(mockEventManager, mockRegistry);
    }

    @Test
    public void HandleEvent_TestRecorderNotStarted_ThrowsException() throws Exception{
        testEventHandler = new IncomingCryptoOnCryptoNetworkWaitingTransferenceExtraUserEventHandler(testRecorderService);
        catchException(testEventHandler).handleEvent(null);
        assertThat(caughtException()).isInstanceOf(TransactionServiceNotStartedException.class);
    }
}
