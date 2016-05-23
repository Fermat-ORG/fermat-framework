package test.com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_bch_api.layer.crypto_vault.bitcoin_vault.CryptoVaultManager;
import com.bitdubai.fermat_api.layer.all_definition.common.system.interfaces.ErrorManager;
import com.bitdubai.fermat_api.layer.all_definition.events.interfaces.FermatEventListener;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.enums.EventType;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.IncomingCryptoTransactionPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_router.incoming_crypto.developer.bitdubai.version_1.structure.IncomingCryptoRegistry;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;


@RunWith(MockitoJUnitRunner.class)
public class ServiceImplementationStartTest extends TestCase {

    @Mock
    ActorAddressBookManager actorAddressBook;

    @Mock
    CryptoVaultManager cryptoVaultManager;

    @Mock
    ErrorManager errorManager;

    @Mock
    EventManager eventManager;

    @Mock
    FermatEventListener fermatEventListener;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    IncomingCryptoRegistry incomingCryptoRegistry;

    @Mock
    Database database;

    @Mock
    DatabaseTableFactory table;

    UUID pluginId;

    IncomingCryptoTransactionPluginRoot incomingCryptoTransactionPluginRoot;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        incomingCryptoTransactionPluginRoot = new IncomingCryptoTransactionPluginRoot();
        incomingCryptoTransactionPluginRoot.setId(pluginId);
        incomingCryptoTransactionPluginRoot.setActorAddressBookManager(actorAddressBook);
        incomingCryptoTransactionPluginRoot.setCryptoVaultManager(cryptoVaultManager);
        incomingCryptoTransactionPluginRoot.setErrorManager(errorManager);
        incomingCryptoTransactionPluginRoot.setEventManager(eventManager);
        incomingCryptoTransactionPluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
        doReturn(fermatEventListener).when(eventManager).getNewListener(any(EventType.class));
    }

    @Test
    public void testStart_ServiceStatusStarted() throws Exception {
        incomingCryptoTransactionPluginRoot.start();
        assertTrue(incomingCryptoTransactionPluginRoot.getStatus() == ServiceStatus.STARTED);
    }

    @Test(expected=CantStartPluginException.class)
    public void testStart_CantInitializeCryptoRegistryException_CantStartPluginException() throws Exception {
        doThrow(new CantOpenDatabaseException(null, null, null, null)).when (pluginDatabaseSystem).openDatabase(any(UUID.class), anyString());

        incomingCryptoTransactionPluginRoot.start();
    }

    /**
     * i cannot test the agents start, because they're never throwing an exception
     */
}
