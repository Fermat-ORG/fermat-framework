package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.ActorAddressBookCryptoModulePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.exceptions.CantGetActorAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.actor_address_book.interfaces.ActorAddressBookRegistry;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.actor_address_book.developer.bitdubai.version_1.ActorAddressBookCryptoModulePluginRoot;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import java.util.UUID;


@RunWith(MockitoJUnitRunner.class)
public class GetActorAddressBookRegistryTest extends TestCase {

    @Mock
    ErrorManager errorManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    UUID pluginId;

    ActorAddressBookCryptoModulePluginRoot actorAddressBookCryptoModulePluginRoot;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        actorAddressBookCryptoModulePluginRoot = new ActorAddressBookCryptoModulePluginRoot();
        actorAddressBookCryptoModulePluginRoot.setId(pluginId);
        actorAddressBookCryptoModulePluginRoot.setErrorManager(errorManager);
        actorAddressBookCryptoModulePluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
    }

    @Test
    public void testGetActorAddressBookRegistryTeste_NotNul() throws CantGetActorAddressBookRegistryException {
        ActorAddressBookRegistry actorAddressBookRegistry = actorAddressBookCryptoModulePluginRoot.getActorAddressBookRegistry();
        assertNotNull(actorAddressBookRegistry);
    }

  /*  @Test(expected=CantGetActorAddressBookRegistryException.class)
    public void testGetActorAddressBookRegistryTest_NotNul() throws CantGetActorAddressBookRegistryException {
        //when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenThrow(CantOpenDatabaseException);
        ActorAddressBookRegistry actorAddressBookRegistry = actorAddressBookCryptoModulePluginRoot.getActorAddressBookRegistry();
        assertNotNull(actorAddressBookRegistry);
    }*/
}
