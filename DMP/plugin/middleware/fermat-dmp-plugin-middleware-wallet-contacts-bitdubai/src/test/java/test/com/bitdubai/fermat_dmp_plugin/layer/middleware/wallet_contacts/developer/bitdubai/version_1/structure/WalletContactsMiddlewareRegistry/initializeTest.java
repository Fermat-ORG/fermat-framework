package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry;

import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDatabaseFactory;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareRegistry;
import com.bitdubai.fermat_pip_api.layer.pip_platform_service.error_manager.ErrorManager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Mockito.when;

/**
 * Created by root on 26/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class initializeTest {
    @Mock
    PluginDatabaseSystem mockPluginDatabaseSystem;
    @Mock
    ErrorManager mockErrorManager;
    @Mock
    Database mockDatabase;

    UUID testPluginId;

    WalletContactsMiddlewareDao walletContactsMiddlewareDao;
    WalletContactsMiddlewareRegistry walletContactsMiddlewareRegistry;

    @Before
    public void setUp() throws Exception {
        testPluginId = UUID.randomUUID();
        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(mockPluginDatabaseSystem);
        walletContactsMiddlewareRegistry = new WalletContactsMiddlewareRegistry();
        walletContactsMiddlewareRegistry.setPluginDatabaseSystem(mockPluginDatabaseSystem);
        walletContactsMiddlewareRegistry.setPluginId(testPluginId);
        walletContactsMiddlewareRegistry.setErrorManager(mockErrorManager);
    }

    @Test
    public void testInitialize_NotNull() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        //walletContactsMiddlewareDao.initializeDatabase(testPluginId, testPluginId.toString());
        walletContactsMiddlewareRegistry.initialize();
    }

    @Test
    public void listWalletContacts_NotNull() throws Exception {
        walletContactsMiddlewareRegistry = new WalletContactsMiddlewareRegistry();

        // when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        //when(walletContactsMiddlewareRegistry.listWalletContacts(testPluginId)).thenReturn(mockDatabase);
        //walletContactsMiddlewareDao.initializeDatabase(testPluginId, testPluginId.toString());
        //walletContactsMiddlewareRegistry.listWalletContacts(testPluginId);
       // walletContactsMiddlewareDao.findAll(testPluginId);
    }
}
