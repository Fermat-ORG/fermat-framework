package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;

import com.bitdubai.fermat_api.CantStartPluginException;
import com.bitdubai.fermat_api.layer.all_definition.enums.ServiceStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.Common.MockedPluginFileSystem;
import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.Common.MockedPluginFileSystemWithError;

import static org.mockito.Mockito.when;

/**
 * Created by rodrigo on 2015.07.15..
 */
@RunWith(MockitoJUnitRunner.class)
public class StartTest {
    @Mock
    ErrorManager errorManager;

    @Mock
    LogManager logManager;

    @Mock
    EventManager eventManager;

    @Mock
    DeviceUserManager deviceUserManager;

    @Mock
    DeviceUser mockDeviceUser;

    private String userPublicKey = "replace_user_public_key";

    @Mock
    BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    MockedPluginFileSystem pluginFileSystem;


    @Test
    public void testValidStart() throws Exception {
        BitcoinCryptoVaultPluginRoot root = new BitcoinCryptoVaultPluginRoot();
        pluginFileSystem = new MockedPluginFileSystem();
        root.setErrorManager(errorManager);
        root.setEventManager(eventManager);
        root.setId(UUID.randomUUID());
        root.setDeviceUserManager(deviceUserManager);
        root.setLogManager(logManager);
        root.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
        root.setPluginDatabaseSystem(pluginDatabaseSystem);
        root.setPluginFileSystem(pluginFileSystem);

        when(deviceUserManager.getLoggedInDeviceUser()).thenReturn(mockDeviceUser);

        when(mockDeviceUser.getPublicKey()).thenReturn(userPublicKey);

        /**
         * I will start it and get a valid address from the vault.
         */
        root.start();
        Assert.assertNotNull(root.getAddress());
    }

    /**
     * I will simulate an error saving the vault to verify the exception.
     * @throws CantStartPluginException
     */
    @Test (expected = CantStartPluginException.class)
    public void testStartWithError() throws Exception {
        BitcoinCryptoVaultPluginRoot root = new BitcoinCryptoVaultPluginRoot();
        MockedPluginFileSystemWithError pluginFileSystemWithError = new MockedPluginFileSystemWithError();
        root.setErrorManager(errorManager);
        root.setEventManager(eventManager);
        root.setId(UUID.randomUUID());
        root.setDeviceUserManager(deviceUserManager);
        root.setLogManager(logManager);
        root.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
        root.setPluginDatabaseSystem(pluginDatabaseSystem);
        root.setPluginFileSystem(pluginFileSystemWithError);

        when(deviceUserManager.getLoggedInDeviceUser()).thenReturn(mockDeviceUser);

        when(mockDeviceUser.getPublicKey()).thenReturn(userPublicKey);
        /**
         * I will start it and get a valid address from the vault.
         */
        root.start();
    }

    @Test
    public void getAddressTest() throws Exception {
        BitcoinCryptoVaultPluginRoot root = new BitcoinCryptoVaultPluginRoot();
        pluginFileSystem = new MockedPluginFileSystem();
        root.setErrorManager(errorManager);
        root.setEventManager(eventManager);
        root.setId(UUID.randomUUID());
        root.setDeviceUserManager(deviceUserManager);
        root.setLogManager(logManager);
        root.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
        root.setPluginDatabaseSystem(pluginDatabaseSystem);
        root.setPluginFileSystem(pluginFileSystem);


        when(deviceUserManager.getLoggedInDeviceUser()).thenReturn(mockDeviceUser);

        when(mockDeviceUser.getPublicKey()).thenReturn(userPublicKey);
        /**
         * I will start it and get a valid address from the vault.
         */
        root.start();

        Assert.assertEquals(root.getAddresses(5).size(), 5);
    }

    @Test
    public void testTransactionProtocolManager() throws Exception {
        BitcoinCryptoVaultPluginRoot root = new BitcoinCryptoVaultPluginRoot();
        pluginFileSystem = new MockedPluginFileSystem();
        root.setErrorManager(errorManager);
        root.setEventManager(eventManager);
        root.setId(UUID.randomUUID());
        root.setDeviceUserManager(deviceUserManager);
        root.setLogManager(logManager);
        root.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
        root.setPluginDatabaseSystem(pluginDatabaseSystem);
        root.setPluginFileSystem(pluginFileSystem);

        when(deviceUserManager.getLoggedInDeviceUser()).thenReturn(mockDeviceUser);

        when(mockDeviceUser.getPublicKey()).thenReturn(userPublicKey);
        /**
         * I will start it and get a valid address from the vault.
         */
        root.start();
        Assert.assertNotNull(root.getTransactionManager());
    }

    @Test
    public void stopTest() throws Exception {
        BitcoinCryptoVaultPluginRoot root = new BitcoinCryptoVaultPluginRoot();
        pluginFileSystem = new MockedPluginFileSystem();
        root.setErrorManager(errorManager);
        root.setEventManager(eventManager);
        root.setId(UUID.randomUUID());
        root.setDeviceUserManager(deviceUserManager);
        root.setLogManager(logManager);
        root.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
        root.setPluginDatabaseSystem(pluginDatabaseSystem);
        root.setPluginFileSystem(pluginFileSystem);

        when(deviceUserManager.getLoggedInDeviceUser()).thenReturn(mockDeviceUser);

        when(mockDeviceUser.getPublicKey()).thenReturn(userPublicKey);

        /**
         * I will start it and get a valid address from the vault.
         */
        root.start();
        root.stop();
        org.junit.Assert.assertEquals(root.getStatus(), ServiceStatus.STOPPED);
    }
}
