package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.event_handlres.IncomingCryptoTransactionsWaitingTransferenceEventHandler;

import com.bitdubai.fermat_api.layer.dmp_transaction.TransactionServiceNotStartedException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.event_handlers.IncomingCryptoTransactionsWaitingTransferenceEventHandler;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoEventRecorderService;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by Franklin Marcano 04/08/15.
 */
public class HandleEventTest {

    @Mock
    private EventManager mockEventManager;

    @Mock
    private IncomingCryptoRegistry mockRegistry;

    @Mock
    private IncomingCryptoEventRecorderService testRecorderService;

    @Mock
    private IncomingCryptoTransactionsWaitingTransferenceEventHandler testEventHandler;

    @Before
    public void setUpRecorderService(){
        testRecorderService = new IncomingCryptoEventRecorderService();
        testRecorderService.setEventManager(mockEventManager);
        testRecorderService.setRegistry(mockRegistry);
    }

    @Test
    public void HandleEvent_TestRecorderNotStarted_ThrowsException() throws Exception{
        testEventHandler = new IncomingCryptoTransactionsWaitingTransferenceEventHandler();
        testEventHandler.setIncomingCryptoEventRecorderService(testRecorderService);
        catchException(testEventHandler).handleEvent(null);
        assertThat(caughtException()).isInstanceOf(TransactionServiceNotStartedException.class);
    }
}
