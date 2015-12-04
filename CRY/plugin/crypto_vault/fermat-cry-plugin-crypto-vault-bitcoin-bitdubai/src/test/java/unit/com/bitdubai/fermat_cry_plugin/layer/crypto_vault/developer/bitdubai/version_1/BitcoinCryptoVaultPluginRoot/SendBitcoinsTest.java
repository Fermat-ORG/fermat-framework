package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;

import com.bitdubai.fermat_api.layer.all_definition.money.CryptoAddress;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotSendMoneyException;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.InvalidSendToAddressException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.interfaces.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.user.device_user.interfaces.DeviceUserManager;


import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.UUID;


import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.Common.MockedPluginFileSystem;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.bitcoinj.core.Address;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created by natalia on 26/08/15.
 */

@RunWith(MockitoJUnitRunner.class)
public class SendBitcoinsTest {


    @Mock
    Database database;

    @Mock
    EventManager eventManager;

    @Mock
    ErrorManager errorManager;

    @Mock
    BitcoinCryptoNetworkManager bitcoinCryptoNetworkManager;

    @Mock
    LogManager logManager;

    @Mock
    PluginDatabaseSystem pluginDatabaseSystem;

    @Mock
    PluginFileSystem pluginFileSystem;

    @Mock
    private List<DatabaseTableRecord> mockRecords;

    @Mock
    private DatabaseTableRecord mockRecord;

    @Mock
    private LogManager mockLogManager;

    @Mock
    private DeviceUserManager mockDeviceUserManager;

    @Mock
    private Address mockAddress;

    @Mock
    DeviceUser mockDeviceUser;

    @Mock
    private Database mockDatabase;

    @Mock
    private DatabaseTable mockTable;

    @Mock
    private CryptoAddress mockCryptoAddress;

    @Mock
    CryptoVaultDatabaseActions mockCryptoVaultDatabaseActions;

    private UUID pluginId = UUID.randomUUID();

    private BitcoinCryptoVaultPluginRoot bitcoinCryptoVaultPluginRoot;

    private String userPublicKey = "replace_user_public_key";

    @Before
    public void setUp() throws Exception{
        bitcoinCryptoVaultPluginRoot = new BitcoinCryptoVaultPluginRoot();
        pluginFileSystem = new MockedPluginFileSystem();
        bitcoinCryptoVaultPluginRoot.setErrorManager(errorManager);
        bitcoinCryptoVaultPluginRoot.setEventManager(eventManager);
        bitcoinCryptoVaultPluginRoot.setId(pluginId);
        bitcoinCryptoVaultPluginRoot.setDeviceUserManager(mockDeviceUserManager);
        bitcoinCryptoVaultPluginRoot.setLogManager(logManager);
        bitcoinCryptoVaultPluginRoot.setBitcoinCryptoNetworkManager(bitcoinCryptoNetworkManager);
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
    public void sendBitcoinsTest_SendGeneralException_ThrowsCouldNotSendMoneyException() throws Exception {

       catchException(bitcoinCryptoVaultPluginRoot).sendBitcoins(UUID.randomUUID().toString(), UUID.randomUUID(), mockCryptoAddress, 100);


        Assertions.assertThat(caughtException())
                .isNotNull().isInstanceOf(CouldNotSendMoneyException.class);
    }

    @Test
    public void sendBitcoinsTest_SendError_ThrowsCouldNotSendMoneyException() throws Exception {

        when(mockCryptoAddress.getAddress()).thenReturn("xxx");
        catchException(bitcoinCryptoVaultPluginRoot).sendBitcoins(UUID.randomUUID().toString(),UUID.randomUUID(),mockCryptoAddress,100);

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(InvalidSendToAddressException.class);
    }

}
