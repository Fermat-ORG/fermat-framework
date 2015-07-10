package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.EventsLauncher;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.all_definition.event.PlatformEvent;
import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.Specialist;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventListener;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventManager;
import com.bitdubai.fermat_api.layer.pip_platform_service.event_manager.EventType;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.CryptoVaultManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.exceptions.SpecialistNotRegisteredException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.util.EventsLauncher;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.EnumSet;
import java.util.Iterator;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;


@RunWith(MockitoJUnitRunner.class)
public class SendEventsTest extends TestCase {

    @Mock
    EventManager eventManager;

    @Mock
    PlatformEvent platformEvent;

    EnumSet<Specialist> specialistEnumSet;


    EventsLauncher eventsLauncher;

    @Before
    public void setUp() throws Exception {
        eventsLauncher = new EventsLauncher();
        eventsLauncher.setEventManager(eventManager);
        doReturn(platformEvent).when(eventManager).getNewEvent(any(EventType.class));

        // add scpecialists
        specialistEnumSet = EnumSet.noneOf(Specialist.class);
        specialistEnumSet.add(Specialist.EXTRA_USER_SPECIALIST);
    }


    // test that the events are raised ok
    @Test
    public void testSendEvents_Success() throws Exception {
        eventsLauncher.sendEvents(specialistEnumSet);
    }

    // test if an specialist is not registered
    @Test(expected = SpecialistNotRegisteredException.class)
    public void testSendEvents_SpecialistNotRegisteredException() throws Exception {
        specialistEnumSet.add(Specialist.UNKNOWN_SPECIALIST);
        eventsLauncher.sendEvents(specialistEnumSet);
    }

    // TODO what happens when there is more of one specialist and one is unknown?
}
