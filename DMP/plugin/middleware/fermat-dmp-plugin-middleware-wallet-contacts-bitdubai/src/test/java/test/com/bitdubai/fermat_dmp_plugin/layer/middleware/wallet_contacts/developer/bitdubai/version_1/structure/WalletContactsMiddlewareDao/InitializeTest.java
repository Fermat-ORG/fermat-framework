package test.com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDao;

import com.bitdubai.fermat_api.layer.dmp_middleware.wallet_contacts.exceptions.CantGetWalletContactException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantCreateDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.CantOpenDatabaseException;
import com.bitdubai.fermat_api.layer.osa_android.database_system.exceptions.DatabaseNotFoundException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.exceptions.CantInitializeWalletContactsDatabaseException;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDao;
import com.bitdubai.fermat_dmp_plugin.layer.middleware.wallet_contacts.developer.bitdubai.version_1.structure.WalletContactsMiddlewareDatabaseFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Created by Nerio on 25/07/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class InitializeTest {

    @Mock
    PluginDatabaseSystem mockPluginDatabaseSystem;

    @Mock
    Database mockDatabase;

    UUID testPluginId;
    WalletContactsMiddlewareDao walletContactsMiddlewareDao;
    WalletContactsMiddlewareDatabaseFactory walletContactsMiddlewareDatabaseFactory;

    @Before
    public void setUp() throws Exception {
        testPluginId = UUID.randomUUID();
        walletContactsMiddlewareDao = new WalletContactsMiddlewareDao(mockPluginDatabaseSystem);
        walletContactsMiddlewareDatabaseFactory = new WalletContactsMiddlewareDatabaseFactory(mockPluginDatabaseSystem);

    }

    @Test
    public void testInitialize_NotNull() throws Exception {
        when(mockPluginDatabaseSystem.openDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
        walletContactsMiddlewareDao.initializeDatabase(testPluginId, testPluginId.toString());
        walletContactsMiddlewareDao.setPluginDatabaseSystem(mockPluginDatabaseSystem);
    }

    // cant open mockDatabase
    @Test(expected = CantInitializeWalletContactsDatabaseException.class)
    public void testInitialize_CantOpenDatabaseException() throws Exception {
        doThrow(new CantOpenDatabaseException()).when(mockPluginDatabaseSystem).openDatabase(any(UUID.class), anyString());
        walletContactsMiddlewareDao.initializeDatabase(testPluginId, testPluginId.toString());
    }

    // mockDatabase not found exception, then cant create mockDatabase.
    @Test(expected = CantInitializeWalletContactsDatabaseException.class)
    public void testInitialize_DatabaseNotFoundException() throws Exception {
        doThrow(new DatabaseNotFoundException()).when(mockPluginDatabaseSystem).openDatabase(any(UUID.class), anyString());
        doThrow(new CantCreateDatabaseException()).when(mockPluginDatabaseSystem).createDatabase(any(UUID.class), anyString());
        walletContactsMiddlewareDao.initializeDatabase(testPluginId, testPluginId.toString());
    }

    @Ignore
    @Test(expected=CantInitializeWalletContactsDatabaseException.class)
    public void testInitialize_DatabaseNotFoundException_CreateDatabase() throws Exception {
        doThrow(new DatabaseNotFoundException()).when(mockPluginDatabaseSystem).openDatabase(any(UUID.class), anyString());
        doThrow(new CantCreateDatabaseException()).when(mockPluginDatabaseSystem).createDatabase(any(UUID.class), anyString());
        when(walletContactsMiddlewareDatabaseFactory.createDatabase(testPluginId, testPluginId.toString())).thenReturn(mockDatabase);
    }
}
