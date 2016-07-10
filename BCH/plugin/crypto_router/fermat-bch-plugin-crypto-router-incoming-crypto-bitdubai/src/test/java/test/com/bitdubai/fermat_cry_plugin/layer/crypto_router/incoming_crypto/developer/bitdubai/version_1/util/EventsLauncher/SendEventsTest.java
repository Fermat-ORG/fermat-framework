package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.EventsLauncher;

import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.SpecialistAndCryptoStatus;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.SpecialistNotRegisteredException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.EventsLauncher;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SendEventsTest extends TestCase {

    @Mock
    SpecialistAndCryptoStatus specialistAndCryptoStatus;

    @Mock
    EventManager eventManager;

    @Mock
    FermatEvent fermatEvent;

    Set<SpecialistAndCryptoStatus> specialistEnumSet;


    EventsLauncher eventsLauncher;

    @Before
    public void setUp() throws Exception {
        eventsLauncher = new EventsLauncher();
        eventsLauncher.setEventManager(eventManager);
        doReturn(fermatEvent).when(eventManager).getNewEvent(any(EventType.class));
        specialistEnumSet = new HashSet<>();
    }

    private void setUpExtraUserSpecialist(){
        when(specialistAndCryptoStatus.getCryptoStatus()).thenReturn(CryptoStatus.ON_CRYPTO_NETWORK);
        when(specialistAndCryptoStatus.getSpecialist()).thenReturn(Specialist.EXTRA_USER_SPECIALIST);

        // add scpecialists
        specialistEnumSet.add(specialistAndCryptoStatus);
    }

    // test that the events are raised ok
    @Test
    public void testSendEvents_Success() throws Exception {
        setUpExtraUserSpecialist();
        eventsLauncher.sendEvents(specialistEnumSet);
    }

    private void setUpUnknownSpecialist(){
        when(specialistAndCryptoStatus.getCryptoStatus()).thenReturn(CryptoStatus.ON_CRYPTO_NETWORK);
        when(specialistAndCryptoStatus.getSpecialist()).thenReturn(Specialist.UNKNOWN_SPECIALIST);

        // add scpecialists
        specialistEnumSet.add(specialistAndCryptoStatus);
    }

    // test if an specialist is not registered
    @Test(expected = SpecialistNotRegisteredException.class)
    public void testSendEvents_SpecialistNotRegisteredException() throws Exception {
        setUpUnknownSpecialist();
        eventsLauncher.sendEvents(specialistEnumSet);
    }

    // TODO what happens when there is more of one specialist and one is unknown?
}
