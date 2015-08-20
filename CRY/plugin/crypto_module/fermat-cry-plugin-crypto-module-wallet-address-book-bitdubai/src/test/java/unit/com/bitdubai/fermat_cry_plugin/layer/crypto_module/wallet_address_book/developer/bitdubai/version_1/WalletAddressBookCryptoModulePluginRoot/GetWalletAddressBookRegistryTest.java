package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.WalletAddressBookCryptoModulePluginRoot;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.exceptions.CantGetWalletAddressBookRegistryException;
import com.bitdubai.fermat_cry_api.layer.crypto_module.wallet_address_book.interfaces.WalletAddressBookRegistry;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.WalletAddressBookCryptoModulePluginRoot;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import java.util.UUID;


@RunWith(MockitoJUnitRunner.class)
public class GetWalletAddressBookRegistryTest extends TestCase {

    @Mock
    ErrorManager errorManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    DatabaseTableFactory mockTable;

    @Mock
    Database mockDatabase;

    @Mock
    DatabaseFactory mockDatabaseFactory;

    UUID pluginId;

    WalletAddressBookCryptoModulePluginRoot walletAddressBookCryptoModulePluginRoot;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        walletAddressBookCryptoModulePluginRoot = new WalletAddressBookCryptoModulePluginRoot();
        walletAddressBookCryptoModulePluginRoot.setId(pluginId);
        walletAddressBookCryptoModulePluginRoot.setErrorManager(errorManager);
        walletAddressBookCryptoModulePluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
    }

    @Test
    public void testGetWalletAddressBookRegistryTest_NotNull() throws Exception {
        when(pluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        WalletAddressBookRegistry walletAddressBookRegistry = walletAddressBookCryptoModulePluginRoot.getWalletAddressBookRegistry();
        assertNotNull(walletAddressBookRegistry);
    }

    @Test(expected=CantGetWalletAddressBookRegistryException.class)
    public void testGetWalletAddressBookRegistryTest_CantOpenDatabaseException() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenThrow(new CantOpenDatabaseException());

        walletAddressBookCryptoModulePluginRoot.getWalletAddressBookRegistry();
    }

    @Test
    public void testGetWalletAddressBookRegistryTest_DatabaseNotFoundException() throws Exception {
        when(pluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenThrow(new DatabaseNotFoundException());
        when(pluginDatabaseSystem.createDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTable);

        WalletAddressBookRegistry walletAddressBookRegistry = walletAddressBookCryptoModulePluginRoot.getWalletAddressBookRegistry();
        assertNotNull(walletAddressBookRegistry);
    }

    @Test(expected=CantGetWalletAddressBookRegistryException.class)
    public void testGetWalletAddressBookRegistryTest_DatabaseNotFoundException_CantCreateDatabaseException() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenThrow(new DatabaseNotFoundException());
        when(pluginDatabaseSystem.createDatabase(pluginId, pluginId.toString())).thenThrow(new CantCreateDatabaseException());

        walletAddressBookCryptoModulePluginRoot.getWalletAddressBookRegistry();
    }
}
