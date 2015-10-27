package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.Common.MockedPluginFileSystem;


import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

/**
 * Created by natalia on 26/08/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class IsValidAddressTest {

    @Mock
    EventManager eventManager;

    @Mock
    ErrorManager errorManager;


    @Mock
    LogManager logManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    BitcoinCryptoNetworkManager mockBitcoinCryptoNetworkManager;

    @Mock
    private DeviceUserManager mockDeviceUserManager;

    @Mock
    DeviceUser mockDeviceUser;

    @Mock
    private Database mockDatabase;

    @Mock
    private DatabaseTable mockTable;

    @Mock
    CryptoVaultDatabaseActions mockCryptoVaultDatabaseActions;

    private UUID pluginId = UUID.randomUUID();

    private BitcoinCryptoVaultPluginRoot bitcoinCryptoVaultPluginRoot;

    private String userPublicKey = "replace_user_public_key";

    @Mock
    private CryptoAddress mockCryptoAddress;

    @Before
    public void setUp() throws Exception{
        bitcoinCryptoVaultPluginRoot = new BitcoinCryptoVaultPluginRoot();
        pluginFileSystem = new MockedPluginFileSystem();
        bitcoinCryptoVaultPluginRoot.setErrorManager(errorManager);
        bitcoinCryptoVaultPluginRoot.setEventManager(eventManager);
        bitcoinCryptoVaultPluginRoot.setId(pluginId);
        bitcoinCryptoVaultPluginRoot.setDeviceUserManager(mockDeviceUserManager);
        bitcoinCryptoVaultPluginRoot.setLogManager(logManager);
        bitcoinCryptoVaultPluginRoot.setBitcoinCryptoNetworkManager(mockBitcoinCryptoNetworkManager);
        bitcoinCryptoVaultPluginRoot.setPluginDatabaseSystem(pluginDatabaseSystem);
        bitcoinCryptoVaultPluginRoot.setPluginFileSystem(pluginFileSystem);

        when(pluginDatabaseSystem.openDatabase(pluginId, userPublicKey)).thenReturn(mockDatabase);

        when(mockDatabase.getTable(anyString())).thenReturn(mockTable);

        when(mockDeviceUserManager.getLoggedInDeviceUser()).thenReturn(mockDeviceUser);

        when(mockDeviceUser.getPublicKey()).thenReturn(userPublicKey);

        when(mockCryptoAddress.getAddress()).thenReturn("mwTdg897T6WEFRnFVm87APwpUeQb6jMgi6");

        bitcoinCryptoVaultPluginRoot.start();
    }

    @Test
    public void isValidAddressTest_ValidFalse() throws Exception {

        when(mockCryptoAddress.getAddress()).thenReturn("xxx");
        boolean isvalid = bitcoinCryptoVaultPluginRoot.isValidAddress(mockCryptoAddress);

        System.out.println(isvalid);
    }

    @Test
    public void isValidAddressTest_ValidTrue() throws Exception {

        boolean isvalid = bitcoinCryptoVaultPluginRoot.isValidAddress(mockCryptoAddress);

        System.out.println(isvalid);
    }




}
