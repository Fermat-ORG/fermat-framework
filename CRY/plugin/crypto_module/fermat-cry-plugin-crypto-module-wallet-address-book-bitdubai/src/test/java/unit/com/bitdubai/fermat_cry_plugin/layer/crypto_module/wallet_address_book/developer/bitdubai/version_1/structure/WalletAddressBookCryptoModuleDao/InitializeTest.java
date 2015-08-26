package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletAddressBookCryptoModuleDao;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableFactory;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.exceptions.CantInitializeWalletAddressBookCryptoModuleException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_module.wallet_address_book.developer.bitdubai.version_1.structure.WalletAddressBookCryptoModuleDao;
import static com.googlecode.catchexception.CatchException.*;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;


import static org.fest.assertions.api.Assertions.*;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class InitializeTest extends TestCase {

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    Database mockDatabase;

    @Mock
    DatabaseTableFactory mockTable;

    @Mock
    DatabaseFactory mockDatabaseFactory;

    WalletAddressBookCryptoModuleDao dao;

    UUID pluginId;

    @Before
    public void setUp() throws Exception {
        pluginId = UUID.randomUUID();
        dao = new WalletAddressBookCryptoModuleDao(pluginDatabaseSystem, pluginId);
        when(pluginDatabaseSystem.openDatabase(any(UUID.class), anyString())).thenReturn(mockDatabase);
        when(mockDatabase.getDatabaseFactory()).thenReturn(mockDatabaseFactory);
        when(mockDatabaseFactory.newTableFactory(any(UUID.class), anyString())).thenReturn(mockTable);
    }

    @Ignore
    @Test
    public void testInitialize_NotNull() throws Exception {
        catchException(dao).initialize();
        assertThat(caughtException()).isNull();
    }

    @Test(expected=CantInitializeWalletAddressBookCryptoModuleException.class)
    public void testInitialize_PluginDatabaseSystemNull_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        dao.setPluginDatabaseSystem(null);
        dao.initialize();
    }

    @Test(expected=CantInitializeWalletAddressBookCryptoModuleException.class)
    public void testInitialize_PluginIdNull_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        dao.setPluginId(null);
        dao.initialize();
    }

    @Test(expected=CantInitializeWalletAddressBookCryptoModuleException.class)
    public void testInitialize_CantCreateDatabaseException_CantInitializeActorAddressBookCryptoModuleException() throws Exception {
        when(pluginDatabaseSystem.openDatabase(pluginId, pluginId.toString())).thenThrow(new CantOpenDatabaseException());
        dao.initialize();
    }
}
