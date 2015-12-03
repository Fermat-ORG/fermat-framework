package unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinCryptoVaultPluginRoot;


import com.bitdubai.fermat_api.layer.all_definition.transaction_transference_protocol.crypto_transactions.CryptoStatus;
import com.bitdubai.fermat_api.layer.osa_android.database_system.Database;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTable;
import com.bitdubai.fermat_api.layer.osa_android.database_system.DatabaseTableRecord;
import com.bitdubai.fermat_api.layer.osa_android.database_system.PluginDatabaseSystem;
import com.bitdubai.fermat_api.layer.osa_android.file_system.PluginFileSystem;
import com.bitdubai.fermat_api.layer.osa_android.logger_system.LogManager;
import com.bitdubai.fermat_cry_api.layer.crypto_network.bitcoin.BitcoinCryptoNetworkManager;
import com.bitdubai.fermat_cry_api.layer.crypto_vault.exceptions.CouldNotGetCryptoStatusException;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.BitcoinPlatformCryptoVaultPluginRoot;
import com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.structure.CryptoVaultDatabaseActions;
import com.bitdubai.fermat_pip_api.layer.platform_service.error_manager.ErrorManager;
import com.bitdubai.fermat_pip_api.layer.platform_service.event_manager.interfaces.EventManager;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUser;
import com.bitdubai.fermat_pip_api.layer.pip_user.device_user.interfaces.DeviceUserManager;

import org.bitcoinj.core.Wallet;

import org.fest.assertions.api.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


import java.util.List;
import java.util.UUID;

import unit.com.bitdubai.fermat_cry_plugin.layer.crypto_vault.developer.bitdubai.version_1.Common.MockedPluginFileSystem;

import static com.googlecode.catchexception.CatchException.catchException;
import static com.googlecode.catchexception.CatchException.caughtException;
import static org.fest.assertions.api.Assertions.assertThat;

import static org.mockito.Matchers.anyString;

import static org.mockito.Mockito.when;

/**
 * Created by natalia on 26/08/15.
 */
@RunWith(MockitoJUnitRunner.class)
public class GetCryptoStatusTest {


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
    private Wallet mockWallet;

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

    private BitcoinPlatformCryptoVaultPluginRoot bitcoinCryptoVaultPluginRoot;

    private String userPublicKey = "replace_user_public_key";

    @Before
    public void setUp() throws Exception{
        bitcoinCryptoVaultPluginRoot = new BitcoinPlatformCryptoVaultPluginRoot();
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

        bitcoinCryptoVaultPluginRoot.start();
    }

    @Ignore
    public void getCryptoStatusTest_GetOk_ThrowsCouldNotGetCryptoStatusException() throws Exception {

        //TODO: necesito un mock que me retorne una lista de records con size = 1
        when(mockTable.getRecords()).thenReturn(mockRecords);
        CryptoStatus status = bitcoinCryptoVaultPluginRoot.getCryptoStatus(UUID.randomUUID());

        Assertions.assertThat(status)
                .isNotNull();
    }

    @Test
    public void getCryptoStatusTest_GetError_ThrowsCouldNotGetCryptoStatusException() throws Exception {
        catchException(bitcoinCryptoVaultPluginRoot).getCryptoStatus(UUID.randomUUID());

        assertThat(caughtException())
                .isNotNull()
                .isInstanceOf(CouldNotGetCryptoStatusException.class);
    }

}
